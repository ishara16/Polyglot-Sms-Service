package main

import (
	"fmt"
	"net/http"

	"github.com/gorilla/mux"
)

func main() {
	connectMongo()
	go startKafkaConsumer()

	r := mux.NewRouter()
	r.HandleFunc("/v1/user/{userId}/messages", getMessages).Methods("GET")

	fmt.Println("Server running on port 8081")
	http.ListenAndServe(":8081", r)
}