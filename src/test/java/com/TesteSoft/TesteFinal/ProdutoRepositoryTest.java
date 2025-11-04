package com.TesteSoft.TesteFinal;

import com.TesteSoft.TesteFinal.model.Produto;
import com.TesteSoft.TesteFinal.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class ProdutoRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private ProdutoRepository repository;

    @Test
    void deveSalvarProdutoNoBancoReal() {
        Produto produto = new Produto(null, "Teclado", 120.0);
        Produto salvo = repository.save(produto);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNome()).isEqualTo("Teclado");
    }
}
