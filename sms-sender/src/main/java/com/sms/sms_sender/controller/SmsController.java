package com.sms.sms_sender.controller;

import com.sms.sms_sender.model.SmsEvent;
import com.sms.sms_sender.service.KafkaProducerService;
import com.sms.sms_sender.service.RedisService;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/v1/sms")
public class SmsController {

    private final KafkaProducerService producerService;
    private final RedisService redisService;

    public SmsController(KafkaProducerService producerService, RedisService redisService) {
        this.producerService = producerService;
        this.redisService = redisService;
    }

    @PostMapping("/send")
    public String sendSMS(@RequestBody SmsEvent request) throws Exception {

        if(redisService.isUserBlocked(request.getUserId())){
            return "User is blocked. SMS not sent.";
        }

        String status = new Random().nextBoolean() ? "SUCCESS" : "FAIL";

        SmsEvent event = new SmsEvent(
                request.getUserId(),
                request.getPhoneNumber(),
                request.getMessage(),
                status
        );

        producerService.sendEvent(event);

        return "SMS processed with status: " + status;
    }
}