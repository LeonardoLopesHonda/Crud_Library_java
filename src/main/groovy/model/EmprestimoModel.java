package model;

import javax.persistence.*;

public class EmprestimoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmprestimo;
    private int idUsuario;
    private String data_emprestimo;
    private String data_devolucao_prevista;
    private String data_devolucao;

    public EmprestimoModel() {
    }

    public EmprestimoModel(String data_emprestimo, String data_devolucao_prevista, String data_devolucao) {
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao_prevista = data_devolucao_prevista;
        this.data_devolucao = data_devolucao;
    }

    public String getData_emprestimo() {
        return data_emprestimo;
    }

    public void setData_emprestimo(String data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }

    public String getData_devolucao_prevista() {
        return data_devolucao_prevista;
    }

    public void setData_devolucao_prevista(String data_devolucao_prevista) {
        this.data_devolucao_prevista = data_devolucao_prevista;
    }

    public String getData_devolucao() {
        return data_devolucao;
    }

    public void setData_devolucao(String data_devolucao) {
        this.data_devolucao = data_devolucao;
    }
}
