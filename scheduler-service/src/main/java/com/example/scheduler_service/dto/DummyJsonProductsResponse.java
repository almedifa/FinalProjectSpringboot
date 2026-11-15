package com.example.scheduler_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DummyJsonProductsResponse {
    private List<DummyJsonProduct> products;
    private int total;
    private int skip;
    private int limit;
}
