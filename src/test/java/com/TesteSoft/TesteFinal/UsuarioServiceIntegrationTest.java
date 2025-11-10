package com.TesteSoft.TesteFinal;

import com.TesteSoft.TesteFinal.exception.EmailAlreadyExistsException;
import com.TesteSoft.TesteFinal.model.Usuario;
import com.TesteSoft.TesteFinal.repository.UsuarioRepository;
import com.TesteSoft.TesteFinal.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
public class UsuarioServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("pass");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void limparBanco() {
        usuarioRepository.deleteAll();
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        Usuario usuario = new Usuario("Pedro", "pedro@teste.com", "1234", "04310000");

        Usuario salvo = usuarioService.cadastraUsuario(usuario);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getEmail()).isEqualTo("pedro@teste.com");
    }

    @Test
    void deveLancarErroAoCadastrarEmailDuplicado() {
        Usuario usuario1 = new Usuario("Pedro", "pedro@teste.com", "1234", "04310000");
        Usuario usuario2 = new Usuario("Outro", "pedro@teste.com", "abcd", "04310000");

        usuarioService.cadastraUsuario(usuario1);

        assertThrows(EmailAlreadyExistsException.class, () -> usuarioService.cadastraUsuario(usuario2));
    }

    @Test
    void deveBuscarUsuarioPorId() {
        Usuario usuario = new Usuario("Everaldo", "everaldo@teste.com", "123", "04310000");
        Usuario salvo = usuarioService.cadastraUsuario(usuario);

        Optional<Usuario> resultado = usuarioService.buscarPorId(salvo.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo("Everaldo");
    }

    @Test
    void deveAtualizarUsuario() {
        Usuario usuario = new Usuario("Julio", "julio@teste.com", "senha1", "04310000");
        Usuario salvo = usuarioService.cadastraUsuario(usuario);

        salvo.setNome("Julio Martins");
        Usuario atualizado = usuarioService.atualizarUsuario(salvo);

        assertThat(atualizado.getNome()).isEqualTo("Julio Martins");
    }

    @Test
    void deveDeletarUsuario() {
        Usuario usuario = new Usuario("Vinicius", "vinicius@teste.com", "senha123", "04310000");
        Usuario salvo = usuarioService.cadastraUsuario(usuario);

        usuarioService.deletarUsuario(salvo.getId());

        assertThat(usuarioRepository.findById(salvo.getId())).isEmpty();
    }
}