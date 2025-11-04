package com.TesteSoft.TesteFinal.repository;

import com.TesteSoft.TesteFinal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
