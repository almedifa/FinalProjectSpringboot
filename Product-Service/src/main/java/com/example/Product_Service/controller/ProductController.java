package com.example.Product_Service.controller;

import com.example.Product_Service.dto.request.ProductRequest;
import com.example.Product_Service.dto.request.ProductImportRequest;
import com.example.Product_Service.dto.response.BaseResponse;
import com.example.Product_Service.dto.response.ProductImportResult;
import com.example.Product_Service.dto.response.ProductResponse;
import com.example.Product_Service.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

@RestController
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductResponse>> create(
            @Valid @RequestBody ProductRequest request,
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("Produk berhasil dibuat", productService.create(request, userId)));
    }

    // Internal scheduler endpoint; the Gateway blocks external access to this path.
    @PostMapping("/internal/import")
    public BaseResponse<ProductImportResult> importProduct(@Valid @RequestBody ProductImportRequest request) {
        return BaseResponse.success("Hasil impor produk", productService.importProduct(request));
    }

    @GetMapping("/{id}")
    public BaseResponse<ProductResponse> findById(@PathVariable Long id) {
        return BaseResponse.success("Produk ditemukan", productService.findById(id));
    }

    @GetMapping
    public BaseResponse<List<ProductResponse>> find(@RequestParam(required = false) String huruf) {
        return BaseResponse.success("Daftar produk berhasil diambil", productService.find(huruf));
    }

    @PutMapping("/{id}")
    public BaseResponse<ProductResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody ProductRequest request) {
        return BaseResponse.success("Produk berhasil diubah", productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return BaseResponse.success("Produk berhasil dihapus", null);
    }
}
