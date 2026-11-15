package com.example.Product_Service.service;

import com.example.Product_Service.dto.request.ProductRequest;
import com.example.Product_Service.dto.request.ProductImportRequest;
import com.example.Product_Service.dto.response.ProductImportResult;
import com.example.Product_Service.dto.response.ProductResponse;
import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request, String userId);
    ProductImportResult importProduct(ProductImportRequest request);
    ProductResponse findById(Long id);
    List<ProductResponse> find(String huruf);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
}
