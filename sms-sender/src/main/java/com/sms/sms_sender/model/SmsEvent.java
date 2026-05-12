package com.sms.sms_sender.model;

public class SmsEvent {
    private String userId;
    private String phoneNumber;
    private String message;
    private String status;

    public SmsEvent() {}

    public SmsEvent(String userId, String phoneNumber, String message, String status) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.status = status;
    }

    public String getUserId() { return userId; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setMessage(String message) { this.message = message; }
    public void setStatus(String status) { this.status = status; }
}
