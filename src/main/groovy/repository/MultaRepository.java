package repository;

import model.EmprestimoModel;
import model.MultaModel;

import javax.persistence.*;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MultaRepository {

    private EntityManager entityManager;

    public MultaRepository() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crud_library");
        entityManager = factory.createEntityManager();
    }

    public void salvar(MultaModel multa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(multa);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void editar(MultaModel multa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(multa);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public String remover(MultaModel multa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(multa);
            entityManager.getTransaction().commit();
            return "Removido com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return "Erro ao remover!\n" + e.getMessage();
        }
    }

    public List<MultaModel> buscarMultasAtivasPorUsuario(Long idUsuario) {
        try {
            return entityManager.createQuery(
                            "SELECT m FROM MultaModel m WHERE m.idUsuario.id = :id AND m.banido = true",
                            MultaModel.class)
                    .setParameter("id", idUsuario)
                    .getResultList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            return new ArrayList<MultaModel>();
        }
    }
}
