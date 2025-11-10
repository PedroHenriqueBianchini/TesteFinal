package com.TesteSoft.TesteFinal.service;

import com.TesteSoft.TesteFinal.model.Produto;
import com.TesteSoft.TesteFinal.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class ProdutoServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("pass");

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void limparBanco() {
        produtoRepository.deleteAll();
    }

    @Test
    void deveSalvarEListarProdutos() {
        Produto produto = new Produto();
        produto.setNome("Coca-Cola 2L");
        produto.setPreco(9.99);

        produtoService.salvar(produto);

        List<Produto> produtos = produtoService.listar();

        assertThat(produtos).isNotEmpty();
        assertThat(produtos.get(0).getNome()).isEqualTo("Coca-Cola 2L");
    }

    @Test
    void deveDeletarProduto() {
        Produto produto = new Produto();
        produto.setNome("Fanta Uva");
        produto.setPreco(8.49);

        Produto salvo = produtoService.salvar(produto);
        produtoService.deletar(salvo.getId());

        List<Produto> produtos = produtoService.listar();
        assertThat(produtos).isEmpty();
    }
}
