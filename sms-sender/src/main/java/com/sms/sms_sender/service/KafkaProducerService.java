package com.sms.sms_sender.service;
import com.sms.sms_sender.model.SmsEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service

public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(SmsEvent event) throws Exception {

        String json = objectMapper.writeValueAsString(event);

        kafkaTemplate.send("sms-events", json);

        System.out.println("Event sent to Kafka: " + json);
    }
}
