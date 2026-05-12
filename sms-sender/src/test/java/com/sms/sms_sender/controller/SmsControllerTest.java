package com.sms.sms_sender.controller;

import com.sms.sms_sender.model.SmsEvent;
import com.sms.sms_sender.service.KafkaProducerService;
import com.sms.sms_sender.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SmsControllerTest {

    @Mock
    private KafkaProducerService kafkaProducerService;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private SmsController smsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void blockedUser_shouldNotSendEvent() throws Exception {
        SmsEvent request = new SmsEvent("user123", "9999999999", "Hello", null);
        when(redisService.isUserBlocked("user123")).thenReturn(true);

        String response = smsController.sendSMS(request);

        assertEquals("User is blocked. SMS not sent.", response);
        verify(kafkaProducerService, never()).sendEvent(any());
    }

    @Test
    void nonBlockedUser_shouldSendEventToKafka() throws Exception {
        SmsEvent request = new SmsEvent("user456", "8888888888", "Hello", null);
        when(redisService.isUserBlocked("user456")).thenReturn(false);

        String response = smsController.sendSMS(request);

        assertTrue(response.startsWith("SMS processed with status:"));
        verify(kafkaProducerService, times(1)).sendEvent(any(SmsEvent.class));
    }

    @Test
    void nonBlockedUser_statusShouldBeSuccessOrFail() throws Exception {
        SmsEvent request = new SmsEvent("user789", "7777777777", "Test", null);
        when(redisService.isUserBlocked("user789")).thenReturn(false);

        String response = smsController.sendSMS(request);

        assertTrue(
            response.contains("SUCCESS") || response.contains("FAIL"),
            "Status should be either SUCCESS or FAIL"
        );
    }
}
