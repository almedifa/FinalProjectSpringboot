package com.example.Product_Service.rest;

import com.example.Product_Service.dto.feign.DummyJsonProductRequest;
import com.example.Product_Service.dto.feign.DummyJsonProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "dummyjson-write-client", url = "${dummyjson.base-url}")
public interface DummyJsonWriteClient {
    @PostMapping("/products/add")
    DummyJsonProductResponse addProduct(@RequestBody DummyJsonProductRequest request);

    @PutMapping("/products/{id}")
    DummyJsonProductResponse updateProduct(@PathVariable("id") Long id,
                                           @RequestBody DummyJsonProductRequest request);

    @DeleteMapping("/products/{id}")
    DummyJsonProductResponse deleteProduct(@PathVariable("id") Long id);
}
