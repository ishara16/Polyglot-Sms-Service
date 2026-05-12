package main
import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)
var collection *mongo.Collection
func connectMongo() {
	client, err := mongo.Connect(context.TODO(),
		options.Client().ApplyURI("mongodb://localhost:27017"))
	if err != nil {
		panic(err)
	}
	collection = client.Database("sms").Collection("messages")
	fmt.Println("Connected to MongoDB")
}
func saveSMS(sms SMSRecord) {
	_, err := collection.InsertOne(context.TODO(), sms)
	if err != nil {
		fmt.Println("Mongo insert error:", err)
		return
	}

	fmt.Println("SMS saved to MongoDB")
}


// This connects to:

// database: sms
// collection: messages