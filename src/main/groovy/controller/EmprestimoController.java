package controller;

import model.EmprestimoModel;
import model.LivroModel;
import model.UsuarioModel;
import repository.EmprestimoRepository;
import repository.LivroRepository;
import repository.UsuarioRepository;

import java.time.LocalDate;

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

        emprestimo.setData_devolucao(LocalDate.now().toString());
        emprestimo.setExpirado(LocalDate.now().isAfter(LocalDate.parse(emprestimo.getData_devolucao_prevista())));

        emprestimoRepository.editar(emprestimo);

        LivroModel livro = emprestimo.getIdLivro();
        livro.setQuantidade_disponivel(livro.getQuantidade_disponivel() + 1);
        livroRepository.editar(livro);

        return "Livro devolvido com sucesso!";
    }
}
