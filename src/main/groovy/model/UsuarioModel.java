package model;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    private String nome;
    private String sexo;
    private String numeroCelular;
    private String email;

    public UsuarioModel() {
    }

    public UsuarioModel(String nome, String sexo, String numeroCelular, String email) {
        this.nome = nome;
        this.sexo = sexo;
        this.numeroCelular = numeroCelular;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
