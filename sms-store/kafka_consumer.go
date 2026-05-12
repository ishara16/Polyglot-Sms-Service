package main
import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/segmentio/kafka-go"
)
func startKafkaConsumer() {
	reader := kafka.NewReader(kafka.ReaderConfig{
		Brokers: []string{"localhost:9092"},
		Topic:   "sms-events",
		GroupID: "sms-store-group",
	})
	for {
		message, err := reader.ReadMessage(context.Background())
		if err != nil {
			fmt.Println(err)
			continue
		}
		fmt.Println("Received message:", string(message.Value))
		var sms SMSRecord
		json.Unmarshal(message.Value, &sms)
		saveSMS(sms)
	}
}