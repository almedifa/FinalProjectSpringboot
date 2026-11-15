package com.example.Product_Service.service.impl;

import com.example.Product_Service.dto.request.ProductRequest;
import com.example.Product_Service.dto.request.ProductImportRequest;
import com.example.Product_Service.dto.feign.DummyJsonProductRequest;
import com.example.Product_Service.dto.feign.DummyJsonProductResponse;
import com.example.Product_Service.dto.response.ProductResponse;
import com.example.Product_Service.dto.response.ProductImportResult;
import com.example.Product_Service.entity.ProductEntity;
import com.example.Product_Service.exception.ProductNotFoundException;
import com.example.Product_Service.repository.ProductRepository;
import com.example.Product_Service.rest.DummyJsonWriteClient;
import com.example.Product_Service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import feign.FeignException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final DummyJsonWriteClient dummyJsonWriteClient;

    @Override
    public ProductResponse create(ProductRequest request, String userId) {
        ProductEntity product = new ProductEntity();
        product.setNama(request.getNama().trim());
        product.setHarga(request.getHarga());
        product.setKategori(request.getKategori().trim());
        product.setBrand(request.getBrand().trim());
        product.setDateAdded(LocalDateTime.now());
        ProductEntity saved = repository.save(product);
        // Manual creation is saved locally first, then mirrored as a best-effort DummyJSON call.
        syncCreate(saved);
        log.info("Product {} created by user {}", saved.getId(), userId);
        return ProductResponse.from(saved);
    }

    @Override
    public ProductImportResult importProduct(ProductImportRequest request) {
        if (request.getRating().compareTo(BigDecimal.valueOf(3)) < 0) {
            log.info("Skipping imported product sourceId={} because rating {} is below 3",
                    request.getSourceId(), request.getRating());
            return new ProductImportResult("SKIPPED_LOW_RATING", null,
                    "Produk dilewati karena rating di bawah 3");
        }

        if (repository.existsBySourceId(request.getSourceId())) {
            log.info("Skipping duplicate imported product sourceId={}", request.getSourceId());
            return new ProductImportResult("DUPLICATE", null, "Produk sudah pernah diimpor");
        }

        ProductEntity product = new ProductEntity();
        product.setSourceId(request.getSourceId());
        product.setNama(request.getNama().trim());
        product.setHarga(request.getHarga());
        product.setKategori(request.getKategori().trim());
        product.setBrand(request.getBrand().trim());
        product.setDateAdded(LocalDateTime.now());
        ProductEntity saved = repository.save(product);
        // W5 imports also create local products, so B4 mirrors their create operation as well.
        syncCreate(saved);
        log.info("Imported product sourceId={} as productId={}", saved.getSourceId(), saved.getId());
        return new ProductImportResult("IMPORTED", saved.getId(), "Produk berhasil diimpor");
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
        ProductEntity saved = repository.save(product);
        syncUpdate(saved);
        return ProductResponse.from(saved);
    }

    @Override
    public void delete(Long id) {
        ProductEntity product = getProduct(id);
        repository.delete(product);
        syncDelete(product);
    }

    private void syncCreate(ProductEntity product) {
        try {
            DummyJsonProductResponse response = dummyJsonWriteClient.addProduct(toDummyJsonRequest(product));
            log.info("DummyJSON add response: {}", response);
        } catch (FeignException exception) {
            log.warn("DummyJSON add failed for local product {} (HTTP {}); local save remains successful",
                    product.getId(), exception.status());
        }
    }

    private void syncUpdate(ProductEntity product) {
        try {
            DummyJsonProductResponse response = dummyJsonWriteClient.updateProduct(
                    getDummyJsonId(product), toDummyJsonRequest(product));
            log.info("DummyJSON update response for product {}: {}", product.getId(), response);
        } catch (FeignException exception) {
            log.warn("DummyJSON update failed for local product {} (HTTP {}); local update remains successful",
                    product.getId(), exception.status());
        }
    }

    private void syncDelete(ProductEntity product) {
        try {
            DummyJsonProductResponse response = dummyJsonWriteClient.deleteProduct(getDummyJsonId(product));
            log.info("DummyJSON delete response for product {}: {}", product.getId(), response);
        } catch (FeignException exception) {
            log.warn("DummyJSON delete failed for local product {} (HTTP {}); local delete remains successful",
                    product.getId(), exception.status());
        }
    }

    private DummyJsonProductRequest toDummyJsonRequest(ProductEntity product) {
        return new DummyJsonProductRequest(product.getNama(), product.getHarga(),
                product.getKategori(), product.getBrand());
    }

    private Long getDummyJsonId(ProductEntity product) {
        return product.getSourceId() == null ? product.getId() : product.getSourceId();
    }

    private ProductEntity getProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

}
