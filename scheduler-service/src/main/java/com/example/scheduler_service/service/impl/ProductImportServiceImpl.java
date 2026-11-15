package com.example.scheduler_service.service.impl;

import com.example.scheduler_service.dto.DummyJsonProduct;
import com.example.scheduler_service.dto.DummyJsonProductsResponse;
import com.example.scheduler_service.dto.ImportSummary;
import com.example.scheduler_service.dto.ProductImportRequest;
import com.example.scheduler_service.dto.ProductImportResult;
import com.example.scheduler_service.dto.BaseResponse;
import com.example.scheduler_service.feign.DummyJsonClient;
import com.example.scheduler_service.feign.ProductServiceClient;
import com.example.scheduler_service.service.ProductImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductImportServiceImpl implements ProductImportService {
    private static final int BATCH_SIZE = 5;

    private final DummyJsonClient dummyJsonClient;
    private final ProductServiceClient productServiceClient;

    // Offset is process state, not product data; the scheduler intentionally has no database.
    private int nextSkip;

    @Override
    public synchronized ImportSummary importNextBatch() {
        int currentSkip = nextSkip;
        try {
            DummyJsonProductsResponse response = dummyJsonClient.getSmartphones(BATCH_SIZE, currentSkip);
            if (response.getProducts() == null || response.getProducts().isEmpty()) {
                nextSkip = 0;
                return new ImportSummary(true, 0, 0, 0, 0, nextSkip,
                        "Tidak ada produk pada batch ini");
            }

            int imported = 0;
            int skippedLowRating = 0;
            int duplicates = 0;

            for (DummyJsonProduct product : response.getProducts()) {
                BaseResponse<ProductImportResult> resultResponse = productServiceClient.importProduct(toRequest(product));
                if (resultResponse == null || resultResponse.getData() == null) {
                    throw new IllegalStateException("Product-Service mengembalikan response impor kosong");
                }

                ProductImportResult result = resultResponse.getData();
                switch (result.getOutcome()) {
                    case "IMPORTED" -> imported++;
                    case "SKIPPED_LOW_RATING" -> skippedLowRating++;
                    case "DUPLICATE" -> duplicates++;
                    default -> throw new IllegalStateException("Outcome impor tidak dikenal: " + result.getOutcome());
                }
            }

            int fetched = response.getProducts().size();
            int total = response.getTotal();
            nextSkip = currentSkip + fetched >= total ? 0 : currentSkip + fetched;
            log.info("Product import batch completed: fetched={}, imported={}, lowRating={}, duplicates={}, nextSkip={}",
                    fetched, imported, skippedLowRating, duplicates, nextSkip);
            return new ImportSummary(true, fetched, imported, skippedLowRating, duplicates, nextSkip,
                    "Batch impor selesai");
        } catch (Exception exception) {
            // Keep the same offset so the failed batch can be retried on the next run.
            log.error("Product import batch failed at skip={}; next run will retry this batch", currentSkip, exception);
            return new ImportSummary(false, 0, 0, 0, 0, currentSkip,
                    "Impor gagal; batch akan dicoba kembali pada putaran berikutnya");
        }
    }

    private ProductImportRequest toRequest(DummyJsonProduct product) {
        ProductImportRequest request = new ProductImportRequest();
        request.setSourceId(product.getId());
        request.setNama(product.getTitle());
        request.setHarga(product.getPrice());
        request.setKategori(product.getCategory());
        request.setBrand(product.getBrand());
        request.setRating(product.getRating());
        return request;
    }
}
