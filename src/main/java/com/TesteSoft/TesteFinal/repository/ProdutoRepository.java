package com.TesteSoft.TesteFinal.repository;

import com.TesteSoft.TesteFinal.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
