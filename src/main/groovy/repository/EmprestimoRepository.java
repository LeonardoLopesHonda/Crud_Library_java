package repository;

import model.EmprestimoModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoRepository {
    private static EmprestimoRepository instance;
    protected EntityManager entityManager;

    public EmprestimoRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crud_library");

        if(entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public static EmprestimoRepository getInstance() {
        if(instance == null) {
            instance = new EmprestimoRepository();
        }

        return instance;
    }

    public String salvar(EmprestimoModel emprestimo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(emprestimo);
            entityManager.getTransaction().commit();
            return "Salvo com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao salvar!\n" + e.getMessage();
        }
    }

    public String editar(EmprestimoModel emprestimo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(emprestimo);
            entityManager.getTransaction().commit();
            return "Editado com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao editar!\n" + e.getMessage();
        }
    }

    public String remover(EmprestimoModel emprestimo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(emprestimo);
            entityManager.getTransaction().commit();
            return "Removido com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao remover!\n" + e.getMessage();
        }
    }

    public List<EmprestimoModel > buscarTodos() {
        try {
            List<EmprestimoModel > emprestimos = entityManager.createQuery("from EmprestimoModel ").getResultList();
            return emprestimos;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public EmprestimoModel buscarPorId(Long id) {
        EmprestimoModel emprestimo = new EmprestimoModel ();
        try {
            emprestimo = entityManager.find(EmprestimoModel.class, id);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return emprestimo;
    }

    public Long contarEmprestimosAtivosPorUsuario(Long idUsuario) {
        try {
            return entityManager.createQuery("SELECT COUNT(emprestimo) FROM  EmprestimoModel emprestimo WHERE emprestimo.idUsuario = :idUsuario", Long.class).setParameter("idUsuario", idUsuario).getSingleResult();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return 0L;
        }
    }

    public boolean livroJaEsmprestado(Long idLivro) {
        try {
            Long count = entityManager.createQuery("SELECT COUNT(emprestimo) FROM  EmprestimoModel emprestimo WHERE emprestimo.idLivro = :idLivro AND emprestimo.data_devolucao IS NULL", Long.class).setParameter("idLivro", idLivro).getSingleResult();
            return count > 0;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
