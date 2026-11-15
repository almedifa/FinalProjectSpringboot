package com.example.Product_Service.producer;

import com.example.Product_Service.dto.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductCreatedProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, ProductCreatedEvent event) {
        try {
            // Analyzer-service publishes String messages; Lombok's event representation keeps this payload readable.
            String payload = event.toString();
            String key = String.valueOf(event.getProductId());
            log.info("Sending product-created event: productId={}, topic={}", key, topic);
            kafkaTemplate.send(topic, key, payload).whenComplete((result, exception) -> {
                if (exception != null) {
                    log.error("Kafka send failed for productId={}", key, exception);
                } else {
                    log.info("Kafka send success: topic={}, partition={}, offset={}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                }
            });
        } catch (RuntimeException exception) {
            // Kafka failure is logged; the already-saved product remains available through the API.
            log.error("Could not publish product-created event for productId={}", event.getProductId(), exception);
        }
    }
}
