package com.TesteSoft.TesteFinal;

import com.TesteSoft.TesteFinal.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsuarioControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + port + "/api/pessoas";
    }

    @Test
    void deveCadastrarListarEBuscarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Controller");
        usuario.setEmail("controller@test.com");
        usuario.setSenha("123456");
        usuario.setCep("01001000"); // CEP válido para integração ViaCEP

        // 1️⃣ Cadastrar usuário
        ResponseEntity<Usuario> responseCreate = restTemplate.postForEntity(baseUrl + "/cadastrar", usuario, Usuario.class);
        assertThat(responseCreate.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseCreate.getBody()).isNotNull();

        int id = responseCreate.getBody().getId();

        // 2️⃣ Listar usuários
        ResponseEntity<Usuario[]> responseList = restTemplate.getForEntity(baseUrl + "/listar", Usuario[].class);
        assertThat(responseList.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseList.getBody()).isNotEmpty();

        // 3️⃣ Buscar por ID
        ResponseEntity<Usuario> responseGet = restTemplate.getForEntity(baseUrl + "/buscar/" + id, Usuario.class);
        assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseGet.getBody().getEmail()).isEqualTo(usuario.getEmail());
    }

    @Test
    void deveAtualizarEDeletarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Atualiza");
        usuario.setEmail("atualiza@test.com");
        usuario.setSenha("abc123");
        usuario.setCep("01001000");

        // Cadastra
        ResponseEntity<Usuario> created = restTemplate.postForEntity(baseUrl + "/cadastrar", usuario, Usuario.class);
        Integer id = created.getBody().getId();

        // 1️⃣ Atualizar nome
        usuario.setNome("Atualizado");
        HttpEntity<Usuario> request = new HttpEntity<>(usuario);
        ResponseEntity<Usuario> updated = restTemplate.exchange(baseUrl + "/atualizar/" + id, HttpMethod.PUT, request, Usuario.class);

        assertThat(updated.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updated.getBody().getNome()).isEqualTo("Atualizado");

        // 2️⃣ Deletar
        restTemplate.delete(baseUrl + "/deletar/" + id);
        ResponseEntity<Usuario> deleted = restTemplate.getForEntity(baseUrl + "/buscar/" + id, Usuario.class);
        assertThat(deleted.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}