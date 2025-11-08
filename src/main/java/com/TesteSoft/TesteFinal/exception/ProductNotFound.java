package com.TesteSoft.TesteFinal.exception;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound(int id) {
        super("Produto: não encontrado com id: " + id);
    }
}