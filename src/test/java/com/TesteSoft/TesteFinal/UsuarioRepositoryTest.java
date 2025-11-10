package com.TesteSoft.TesteFinal;

import com.TesteSoft.TesteFinal.model.Usuario;
import com.TesteSoft.TesteFinal.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class UsuarioRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private UsuarioRepository repository;

    @Test
    void deveSalvarUsuarioNoBancoReal() {
        Usuario usuario = new Usuario("Pedro Bianchini", "pedro@email.com", "123", "13500000");

        Usuario salvo = repository.save(usuario);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNome()).isEqualTo("Pedro Bianchini");
        assertThat(salvo.getEmail()).isEqualTo("pedro@email.com");
        assertThat(salvo.getSenha()).isEqualTo("123");
        assertThat(salvo.getCep()).isEqualTo("13500000");
    }
}
