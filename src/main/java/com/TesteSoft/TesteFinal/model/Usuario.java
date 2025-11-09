package com.TesteSoft.TesteFinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(length = 8, nullable = false)
    private String cep;

    @Column
    private String logradouro;

    @Column
    private String bairro;

    @Column
    private String localidade;

    @Column(length = 2)
    private String uf;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha, String cep) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cep = cep;
    }
}
