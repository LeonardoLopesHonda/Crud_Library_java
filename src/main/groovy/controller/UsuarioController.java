package controller;

import model.UsuarioModel;
import repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    public String salvar(UsuarioModel usuario) throws SQLException {
        return usuarioRepository.salvar(usuario);
    }

    public String editar(UsuarioModel usuario) throws SQLException {
        return usuarioRepository.editar(usuario);
    }

    public String excluir(Long idUsuario) throws SQLException {
        UsuarioModel usuario = usuarioRepository.buscarPorId(idUsuario);
        return usuarioRepository.remover(usuario);
    }

    public List<UsuarioModel> listar() throws SQLException {
        return usuarioRepository.buscarTodos();
    }
}
