package model;

import javax.persistence.*;

@Entity
@Table(name = "multas")
public class MultaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idUsuario")

    private UsuarioModel idUsuario;
    private Boolean banido;
    private String data_de_multa;
    private String data_fim_multa;

    public MultaModel(UsuarioModel idUsuario, Boolean banido, String data_de_multa, String data_fim_multa) {
        this.idUsuario = idUsuario;
        this.banido = banido;
        this.data_de_multa = data_de_multa;
        this.data_fim_multa = data_fim_multa;
    }

    public MultaModel() {

    }

    public UsuarioModel getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuarioModel idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getBanido() {
        return banido;
    }

    public void setBanido(Boolean banido) {
        this.banido = banido;
    }

    public String getData_de_multa() {
        return data_de_multa;
    }

    public void setData_de_multa(String data_de_multa) {
        this.data_de_multa = data_de_multa;
    }

    public String getData_fim_multa() {
        return data_fim_multa;
    }

    public void setData_fim_multa(String data_fim_multa) {
        this.data_fim_multa = data_fim_multa;
    }
}
