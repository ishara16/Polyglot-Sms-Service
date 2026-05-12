package main

import "testing"

func TestSMSRecordFields(t *testing.T) {

	sms := SMSRecord{
		UserID:     "user123",
		PhoneNumber: "9999999999",
		Message:    "Hello",
		Status:     "SUCCESS",
	}

	if sms.UserID != "user123" {
		t.Errorf("expected user123, got %s", sms.UserID)
	}

	if sms.PhoneNumber != "9999999999" {
		t.Errorf("expected 9999999999, got %s", sms.PhoneNumber)
	}

	if sms.Message != "Hello" {
		t.Errorf("expected Hello, got %s", sms.Message)
	}

	if sms.Status != "SUCCESS" {
		t.Errorf("expected SUCCESS, got %s", sms.Status)
	}
}