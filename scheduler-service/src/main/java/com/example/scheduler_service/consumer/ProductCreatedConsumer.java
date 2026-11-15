package com.example.scheduler_service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductCreatedConsumer {

    @KafkaListener(
            topics = "${kafka.topic.product-created}",
            groupId = "${kafka.consumer.group-id}"
    )
    public void onProductCreated(String event) {
        log.info("Consumed product-created event: {}", event);
    }
}
