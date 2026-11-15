package com.example.Product_Service.service.impl;

import com.example.Product_Service.dto.request.ProductRequest;
import com.example.Product_Service.dto.response.ProductResponse;
import com.example.Product_Service.entity.ProductEntity;
import com.example.Product_Service.exception.ProductNotFoundException;
import com.example.Product_Service.repository.ProductRepository;
import com.example.Product_Service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public ProductResponse create(ProductRequest request, String userId) {
        ProductEntity product = new ProductEntity();
        product.setNama(request.getNama().trim());
        product.setHarga(request.getHarga());
        product.setKategori(request.getKategori().trim());
        product.setBrand(request.getBrand().trim());
        ProductResponse response = ProductResponse.from(repository.save(product));
        log.info("Product {} created by user {}", response.getId(), userId);
        return response;
    }

    @Override
    public ProductResponse findById(Long id) {
        return ProductResponse.from(getProduct(id));
    }

    @Override
    public List<ProductResponse> find(String huruf) {
        List<ProductEntity> products = huruf == null || huruf.isBlank()
                ? repository.findAll()
                : repository.findByNamaContainingIgnoreCase(huruf.trim());
        return products.stream().map(ProductResponse::from).toList();
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        ProductEntity product = getProduct(id);
        product.setNama(request.getNama().trim());
        product.setHarga(request.getHarga());
        product.setKategori(request.getKategori().trim());
        product.setBrand(request.getBrand().trim());
        return ProductResponse.from(repository.save(product));
    }

    @Override
    public void delete(Long id) {
        repository.delete(getProduct(id));
    }

    private ProductEntity getProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

}
