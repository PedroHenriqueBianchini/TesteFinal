package com.TesteSoft.TesteFinal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Email
    private String email;

    @NotBlank
    private String senha;

    private String cep;

    public Usuario(String nome, String email, String senha, String cep) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cep = cep;
    }
}
