package main

type SMSRecord struct {
	UserID      string `json:"userId" bson:"userId"`
	PhoneNumber string `json:"phoneNumber" bson:"phoneNumber"`
	Message     string `json:"message" bson:"message"`
	Status      string `json:"status" bson:"status"`
}


// This represents the SMS document stored in MongoDB.