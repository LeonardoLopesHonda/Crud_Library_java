package controller;

import model.LivroModel;
import repository.LivroRepository;

import java.sql.SQLException;
import java.util.List;

public class LivroController {
    private LivroRepository livroRepository = new LivroRepository();

    public String salvar(LivroModel livro) throws SQLException {
        if(livro.getIdLivro() == null) {
            return livroRepository.salvar(livro);
        } else {
            return livroRepository.editar(livro);
        }
    }

    public String editar(LivroModel livro) throws SQLException {
        return livroRepository.editar(livro);
    }

    public String excluir(Long idLivro) throws SQLException {
        LivroModel livro = livroRepository.buscarPorId(idLivro);
        return livroRepository.remover(livro);
    }

    public List<LivroModel> listar() throws SQLException {
        return livroRepository.buscarTodos();
    }

    public List<LivroModel> buscarDisponiveis() throws SQLException {
        return livroRepository.buscarLivrosDisponiveis();
    }
}
