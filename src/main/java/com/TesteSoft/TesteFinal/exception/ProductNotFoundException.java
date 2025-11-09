package com.TesteSoft.TesteFinal.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(int id) {
        super("Produto: não encontrado com id: " + id);
    }
}