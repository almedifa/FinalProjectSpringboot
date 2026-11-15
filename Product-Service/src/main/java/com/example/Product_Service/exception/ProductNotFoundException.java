package com.example.Product_Service.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) { super("Produk dengan id " + id + " tidak ditemukan"); }
}
