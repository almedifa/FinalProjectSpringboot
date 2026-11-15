package com.example.scheduler_service.feign;

import com.example.scheduler_service.dto.DummyJsonProductsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dummyjson-client", url = "${dummyjson.base-url}")
public interface DummyJsonClient {
    @GetMapping("/products/category/smartphones")
    DummyJsonProductsResponse getSmartphones(@RequestParam("limit") int limit,
                                             @RequestParam("skip") int skip);
}
