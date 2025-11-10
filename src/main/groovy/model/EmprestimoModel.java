package model;

import javax.persistence.*;

@Entity
@Table(name = "emprestimos")
public class EmprestimoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmprestimo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idLivro")
    private LivroModel idLivro;

    private String data_emprestimo;
    private String data_devolucao_prevista;
    private String data_devolucao;
    private boolean expirado;

    public EmprestimoModel() {
    }

    public EmprestimoModel(String data_emprestimo, String data_devolucao_prevista, String data_devolucao) {
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao_prevista = data_devolucao_prevista;
        this.data_devolucao = data_devolucao;
    }

    public Long getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Long idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public UsuarioModel getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuarioModel idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LivroModel getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(LivroModel idLivro) {
        this.idLivro = idLivro;
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

    public boolean isExpirado() {
        return expirado;
    }

    public void setExpirado(boolean expirado) {
        this.expirado = expirado;
    }
}
