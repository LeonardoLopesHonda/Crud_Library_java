package repository;

import model.LivroModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    private static LivroRepository instance;
    protected EntityManager entityManager;

    public LivroRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crud_library");

        if(entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public static LivroRepository getInstance() {
        if(instance == null) {
            instance = new LivroRepository();
        }

        return instance;
    }

    public String salvar(LivroModel livro) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(livro);
            entityManager.getTransaction().commit();
            return "Salvo com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao salvar!\n" + e.getMessage();
        }
    }

    public String editar(LivroModel livro) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(livro);
            entityManager.getTransaction().commit();
            return "Editado com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao editar!\n" + e.getMessage();
        }
    }

    public String remover(LivroModel livro) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(livro);
            entityManager.getTransaction().commit();
            return "Removido com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao remover!\n" + e.getMessage();
        }
    }

    public List<LivroModel > buscarTodos() {
        try {
            List<LivroModel > livros = entityManager.createQuery("from LivroModel ").getResultList();
            return livros;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public LivroModel buscarPorId(Long id) {
        LivroModel livro = new LivroModel ();
        try {
            livro = entityManager.find(LivroModel .class, id);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return livro;
    }

    public List<LivroModel > buscarPorNome(String nome) {
        List<LivroModel > livros = new ArrayList<>();
        try {
            livros = entityManager.createQuery("from LivroModel where titulo like '%" + nome + "%'").getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return livros;
    }
}
