package main
import (
	"context"
	"encoding/json"
	"net/http"
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson"
)
func getMessages(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	userId := vars["userId"]
	w.Header().Set("Content-Type", "application/json")
	if userId == "" {
		http.Error(w, "userId is required", http.StatusBadRequest)
		return
	}
	cursor, err := collection.Find(context.TODO(), bson.M{"userId": userId})
	if err != nil {
		http.Error(w, "Failed to fetch messages", http.StatusInternalServerError)
		return
	}
	var results []SMSRecord
	if err = cursor.All(context.TODO(), &results); err != nil {
		http.Error(w, "Failed to decode messages", http.StatusInternalServerError)
		return
	}
	json.NewEncoder(w).Encode(results)
}