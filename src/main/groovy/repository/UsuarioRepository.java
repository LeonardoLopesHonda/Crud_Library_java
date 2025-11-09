package repository;

import model.UsuarioModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private static UsuarioRepository instance;
    protected EntityManager entityManager;

    public UsuarioRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("usuario");

        if(entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public static UsuarioRepository getInstance() {
        if(instance == null) {
            instance = new UsuarioRepository();
        }

        return instance;
    }

    public String salvar(UsuarioModel usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
            return "Salvo com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao salvar!\n" + e.getMessage();
        }
    }

    public String editar(UsuarioModel usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
            return "Editado com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao editar!\n" + e.getMessage();
        }
    }

    public String remover(UsuarioModel usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(usuario);
            entityManager.getTransaction().commit();
            return "Removido com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao remover!\n" + e.getMessage();
        }
    }

    public List<UsuarioModel> buscarTodos() {
        try {
            List<UsuarioModel> usuarios = entityManager.createQuery("from UsuarioModel ").getResultList();
            return usuarios;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public UsuarioModel buscarPorId(Long id) {
        UsuarioModel usuario = new UsuarioModel();
        try {
            usuario = entityManager.find(UsuarioModel.class, id);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return usuario;
    }

    public List<UsuarioModel> buscarPorNome(String nome) {
        List<UsuarioModel> usuarios = new ArrayList<>();
        try {
            usuarios = entityManager.createQuery("from UsuarioModel where nome like '%" + nome + "%'").getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return usuarios;
    }
}
