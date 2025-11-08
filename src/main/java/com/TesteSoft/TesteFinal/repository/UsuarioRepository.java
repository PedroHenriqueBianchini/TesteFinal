package com.TesteSoft.TesteFinal.repository;

import com.TesteSoft.TesteFinal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<com.TesteSoft.TesteFinal.model.Usuario, Integer> {
    boolean existsByEmail(String email);
}