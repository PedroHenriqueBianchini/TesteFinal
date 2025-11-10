package com.TesteSoft.TesteFinal.controller;

import com.TesteSoft.TesteFinal.model.Usuario;
import com.TesteSoft.TesteFinal.model.ViaCEP;
import com.TesteSoft.TesteFinal.service.UsuarioService;
import com.TesteSoft.TesteFinal.vcr.VCRViaCEPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VCRViaCEPService viaCEPService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        if (usuario.getCep() != null && !usuario.getCep().isBlank()) {
            ViaCEP viaCEP = viaCEPService.buscarEnderecoPorCEP(usuario.getCep(), "usuario_cadastro", true);
            System.out.printf("Endereço encontrado: %s, %s - %s/%s%n",
                    viaCEP.getLogradouro(), viaCEP.getBairro(),
                    viaCEP.getLocalidade(), viaCEP.getUf());
        }

        Usuario salvo = usuarioService.cadastraUsuario(usuario);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario atualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
