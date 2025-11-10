package com.TesteSoft.TesteFinal.controller;

import com.TesteSoft.TesteFinal.exception.EmailAlreadyExistsException;
import com.TesteSoft.TesteFinal.model.Usuario;
import com.TesteSoft.TesteFinal.DTO.ViaCEP;
import com.TesteSoft.TesteFinal.service.UsuarioService;
import com.TesteSoft.TesteFinal.service.ViaCEPService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    public final UsuarioService usuarioService;
    private final ViaCEPService viaCEPService;

    public UsuarioController(UsuarioService usuarioService, ViaCEPService viaCEPService) {
        this.usuarioService = usuarioService;
        this.viaCEPService = viaCEPService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            ViaCEP endereco = viaCEPService.buscarEnderecoPorCEP(usuario.getCep());

            usuario.setLogradouro(endereco.getLogradouro());
            usuario.setBairro(endereco.getBairro());
            usuario.setLocalidade(endereco.getLocalidade());
            usuario.setUf(endereco.getUf());

            Usuario novoUsuario = usuarioService.cadastraUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);

        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable int id) {
        Optional<Usuario> pessoa = usuarioService.buscarPorId(id);
        return pessoa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuario> atualizarPessoa(@PathVariable int id, @RequestBody Usuario usuario) {
        usuario.setId(id);

        if (usuario.getCep() != null && !usuario.getCep().isBlank()) {
            try {
                ViaCEP endereco = viaCEPService.buscarEnderecoPorCEP(usuario.getCep());
                usuario.setLogradouro(endereco.getLogradouro());
                usuario.setBairro(endereco.getBairro());
                usuario.setLocalidade(endereco.getLocalidade());
                usuario.setUf(endereco.getUf());
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }
        }

        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok(usuarioAtualizado);

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable int id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}