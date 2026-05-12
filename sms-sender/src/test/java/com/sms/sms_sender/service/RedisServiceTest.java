package com.sms.sms_sender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @InjectMocks
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void blockedUser_shouldReturnTrue() {
        when(redisTemplate.hasKey("blocked:user123")).thenReturn(true);
        assertTrue(redisService.isUserBlocked("user123"));
    }

    @Test
    void nonBlockedUser_shouldReturnFalse() {
        when(redisTemplate.hasKey("blocked:user456")).thenReturn(false);
        assertFalse(redisService.isUserBlocked("user456"));
    }

    @Test
    void nullRedisResponse_shouldReturnFalse() {
        when(redisTemplate.hasKey("blocked:user789")).thenReturn(null);
        assertFalse(redisService.isUserBlocked("user789"));
    }
}
