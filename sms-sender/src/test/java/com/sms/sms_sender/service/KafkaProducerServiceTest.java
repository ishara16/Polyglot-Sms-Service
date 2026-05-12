package com.sms.sms_sender.service;

import com.sms.sms_sender.model.SmsEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEvent_shouldSendToCorrectTopic() throws Exception {
        SmsEvent event = new SmsEvent("user123", "9999999999", "Hello", "SUCCESS");

        kafkaProducerService.sendEvent(event);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(topicCaptor.capture(), messageCaptor.capture());

        assertEquals("sms-events", topicCaptor.getValue());
    }

    @Test
    void sendEvent_shouldSerialiseAllFields() throws Exception {
        SmsEvent event = new SmsEvent("user123", "9999999999", "Hello", "SUCCESS");

        kafkaProducerService.sendEvent(event);

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(anyString(), messageCaptor.capture());

        String json = messageCaptor.getValue();
        assertTrue(json.contains("user123"));
        assertTrue(json.contains("9999999999"));
        assertTrue(json.contains("Hello"));
        assertTrue(json.contains("SUCCESS"));
    }
}
