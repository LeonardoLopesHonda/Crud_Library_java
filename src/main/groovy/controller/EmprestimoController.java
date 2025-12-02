package controller;

import model.EmprestimoModel;
import model.LivroModel;
import model.MultaModel;
import model.UsuarioModel;
import repository.EmprestimoRepository;
import repository.LivroRepository;
import repository.MultaRepository;
import repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoController {
    private EmprestimoRepository emprestimoRepository;
    private UsuarioRepository usuarioRepository;
    private LivroRepository livroRepository;

    public EmprestimoController() {
        emprestimoRepository = new EmprestimoRepository();
        usuarioRepository = new UsuarioRepository();
        livroRepository = new LivroRepository();
    }

    public String emprestar(Long idUsuario, Long idLivro) {
        UsuarioModel usuario = usuarioRepository.buscarPorId(idUsuario);
        LivroModel livro = livroRepository.buscarPorId(idLivro);

        if(usuario == null || livro == null) {
            return "Usuário ou livro não encontrado";
        }

        Long emprestimosAtivos = emprestimoRepository.contarEmprestimosAtivosPorUsuario(idUsuario);
        if(emprestimosAtivos >= 5) {
            return "O usuário já tem 5 empréstimos ativos.";
        }

        if(livro.getQuantidade_disponivel() <= 0) {
            return "O livro não tem exemplares em estoque.";
        }

        MultaRepository multaRepository = new MultaRepository();
        List<MultaModel> multas = multaRepository.buscarMultasAtivasPorUsuario(idUsuario);
        if(!multas.isEmpty()) {
            MultaModel multa = multas.get(0);
            LocalDate fim_multa = LocalDate.parse(multa.getData_fim_multa());
            LocalDate hoje = LocalDate.now();
            if(hoje.isBefore(fim_multa)) {
                return "Usuário banido até " + fim_multa;
            } else {
                multa.setBanido(true);
                multaRepository.editar(multa);
            }
        }

        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setIdUsuario(usuario);
        emprestimo.setIdLivro(livro);
        emprestimo.setData_emprestimo(LocalDate.now().toString());
        emprestimo.setData_devolucao_prevista(LocalDate.now().plusDays(14).toString());
        emprestimo.setExpirado(false);

        String resultado = emprestimoRepository.salvar(emprestimo);

        livro.setQuantidade_disponivel(livro.getQuantidade_disponivel() - 1);
        livroRepository.editar(livro);

        return resultado;
    }

    public String devolver(Long idEmprestimo) {
        EmprestimoModel emprestimo = emprestimoRepository.buscarPorId(idEmprestimo);
        if(emprestimo == null) {
            return "Empréstimo não encontrado!";
        }

        if(emprestimo.getData_devolucao() != null) {
            return "O livro já foi devolvido!";
        }

        var hoje = LocalDate.now();
        LocalDate prevista = LocalDate.parse(emprestimo.getData_devolucao_prevista());

        emprestimo.setData_devolucao(hoje.toString());
        emprestimo.setExpirado(hoje.isAfter(LocalDate.parse(emprestimo.getData_devolucao_prevista())));

        emprestimoRepository.editar(emprestimo);

        LivroModel livro = emprestimo.getIdLivro();
        livro.setQuantidade_disponivel(livro.getQuantidade_disponivel() + 1);
        livroRepository.editar(livro);

        if (hoje.isAfter(prevista)) {
            aplicarMulta(emprestimo.getIdUsuario());
            return "Livro devolvido com sucesso, mas o usuario foi BANIDO por atraso :)";
        }

        return "Livro devolvido com sucesso!";
    }

    private void aplicarMulta(UsuarioModel usuario) {
        MultaModel multa =  new MultaModel(usuario, true, LocalDate.now().toString(), LocalDate.now().plusDays(7).toString());
        MultaRepository multaRepository = new MultaRepository();
        try {
            multaRepository.salvar(multa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
