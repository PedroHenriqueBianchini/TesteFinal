package com.TesteSoft.TesteFinal;

import com.TesteSoft.TesteFinal.controller.UsuarioController;
import com.TesteSoft.TesteFinal.model.Usuario;
import com.TesteSoft.TesteFinal.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarUsuarios() {
        when(usuarioService.listarTodos()).thenReturn(List.of(new Usuario("Kenzo", "kenzo@teste.com", "123", "04310000")));

        ResponseEntity<List<Usuario>> resposta = usuarioController.listarTodos();

        assertThat(resposta.getBody()).hasSize(1);
        verify(usuarioService, times(1)).listarTodos();
    }

    @Test
    void deveAtualizarUsuario() {
        Usuario usuario = new Usuario("Teste", "teste@teste.com", "senha", "04310000");
        usuario.setId(1L);

        when(usuarioService.atualizarUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> resposta = usuarioController.atualizar(1L, usuario);

        assertThat(resposta.getBody().getId()).isEqualTo(1L);
        verify(usuarioService, times(1)).atualizarUsuario(any(Usuario.class));
    }

    @Test
    void deveDeletarUsuario() {
        doNothing().when(usuarioService).deletarUsuario(anyLong());

        ResponseEntity<Void> resposta = usuarioController.deletar(1L);

        assertThat(resposta.getStatusCode().is2xxSuccessful()).isTrue();
        verify(usuarioService, times(1)).deletarUsuario(1L);
    }
}
