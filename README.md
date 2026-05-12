# Polyglot Distributed SMS Processing System

A distributed SMS processing system built using Java, Go, Kafka, Redis, and MongoDB.

## Architecture

This project contains two independent microservices:

### 1. sms-sender (Java + Spring Boot)
Responsible for:
- Receiving SMS requests
- Validating blocked numbers using Redis
- Simulating SMS vendor processing
- Publishing SMS events to Kafka

### 2. sms-store (Go)
Responsible for:
- Consuming Kafka messages
- Persisting SMS records into MongoDB
- Providing REST API for querying stored messages

---

# Tech Stack

| Component | Technology |
|---|---|
| API Service | Java Spring Boot |
| Consumer Service | Go |
| Message Broker | Apache Kafka |
| Cache | Redis |
| Database | MongoDB |
| Testing | JUnit, Mockito, Go testing |
| Communication | REST + Kafka |

---

# System Flow

1. Client sends SMS request to Java service
2. Java service checks blocked numbers in Redis
3. SMS status is generated (SUCCESS/FAIL)
4. Event is published to Kafka
5. Go service consumes Kafka message
6. Message stored in MongoDB
7. Client fetches stored messages through Go API

---

# APIs

## Send SMS

### Endpoint

```http
POST /v1/sms/send
```

### Request Body

```json
{
  "userId": "user123",
  "phoneNumber": "9999999999",
  "message": "Hello World"
}
```

### Sample Response

```text
SMS processed with status: SUCCESS
```

---

## Fetch User Messages

### Endpoint

```http
GET /v1/user/{userId}/messages
```

### Example

```http
GET /v1/user/user123/messages
```

### Sample Response

```json
[
  {
    "userId": "user123",
    "phoneNumber": "9999999999",
    "message": "Hello World",
    "status": "SUCCESS"
  }
]
```

---

# Project Structure

```text
smsService/
│
├── sms-sender/       # Java Spring Boot producer service
├── sms-store/        # Go Kafka consumer service
└── README.md
```

---

# Setup Instructions

## Prerequisites

Install:
- Java 21+
- Go 1.21+
- Maven
- Apache Kafka
- Redis
- MongoDB

---

# DEMONSTRATION

## Step 1. Start Kafka

Open terminal inside Kafka folder:
```bash
bin/windows/kafka-server-start.bat config/server.properties
```

---

## Step 2. Start Redis

```bash
redis-server
```

---

## Step 3. Start MongoDB

```bash
mongod --dbpath "C:\Users\Lenovo\Projects\smsService\data\db"
```

---

## Step 4. Start Go SMS Store Service

Open terminal:
```bash
cd sms-store
go run .
```

Expected Output:
```bash
Connected to MongoDB
Server running on port 8081
```
---

## Step 5. Start Java SMS Sender Service

Open another terminal:
```bash
cd sms-sender
./mvnw.cmd spring-boot:run
```
Expected Output:
```bash
Started SmsSenderApplication
```
---

## Step 6. Send SMS Request

Open another terminal:
```bash
curl -X POST http://localhost:8080/v1/sms/send \
-H "Content-Type: application/json" \
-d "{\"userId\":\"user123\",\"phoneNumber\":\"9999999999\",\"message\":\"Hello World\"}"
```

Expected Output:
```bash
SMS processed with status: SUCCESS
```
(or FAIL)


## Step 7. Verify GoLang Service Consumed Kafka Event

Check Go service terminal.
Expected Logs:
```bash
Received message: {"userId":"user123","phoneNumber":"9999999999","message":"Hello World","status":"SUCCESS"}

SMS saved to MongoDB
```

## Step 8. Retrieve SMS History

Run:
```bash
curl "http://localhost:8081/v1/user/user123/messages"
```
Expected Response:
```json
[
  {
    "userId": "user123",
    "phoneNumber": "9999999999",
    "message": "Hello World",
    "status": "SUCCESS"
  }
]
```

---

# Unit Tests

## Java Tests

```bash
./mvnw.cmd test
```

## Go Tests

```bash
go test ./...
```

---

# Features

- Distributed microservice architecture
- Polyglot implementation (Java + Go)
- Kafka asynchronous communication
- Redis-based validation
- MongoDB persistence
- REST APIs
- Unit testing
- Fault-tolerant event-driven workflow

---

# Future Improvements

- Docker support
- Kubernetes deployment
- Retry mechanism
- Dead Letter Queue (DLQ)
- Swagger/OpenAPI documentation
- Authentication and authorization

---

# Author

Isha Raut