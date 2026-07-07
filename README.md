# 🏦 FinGuard Enterprise

> **Enterprise Banking Microservices Platform built with Spring Boot, Kafka, Docker, Eureka, API Gateway, MySQL, and JWT Authentication.**

FinGuard Enterprise is a production-inspired banking backend designed using a **Microservices Architecture**. The project demonstrates enterprise software engineering practices including event-driven communication with Kafka, service discovery using Eureka, API Gateway routing, Dockerized deployment, JWT authentication, and independent databases for each microservice.

> **Project Status:** 🚧 Active Development

---

# 🚀 Tech Stack

### Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka
- Spring Kafka
- JWT Authentication

### Database

- MySQL 8
- Independent Database per Microservice

### Messaging

- Apache Kafka
- Event-Driven Architecture

### Infrastructure

- Docker
- Docker Compose

### Tools

- Maven
- Postman
- MySQL Workbench
- Git
- GitHub

---

# 📌 Current Microservices

| Service | Port | Description |
|----------|------|-------------|
| API Gateway | 8080 | Single entry point |
| Eureka Server | 8761 | Service Discovery |
| Auth Service | 8081 | Authentication & JWT |
| User Service | 8082 | User Profile Management |
| Transaction Service | 8083 | Banking Operations |
| Notification Service | 8084 | Email Notifications |
| Loan Service | 8085 | Loan Management |
| Kafka UI | 8088 | Kafka Monitoring |

---

# 🏗 Current Architecture

```
                    Client
                      │
                      ▼
               API Gateway
                      │
 ┌──────────┬─────────┼─────────┬────────────┬────────────┐
 ▼          ▼         ▼         ▼            ▼
Auth      User    Transaction  Loan   Notification
Service   Service   Service    Service   Service
      │
      ▼
   Eureka Server

Kafka enables asynchronous communication between services.

Each service owns its own database.
```

---

# ✨ Current Features

## Authentication

- User Registration
- Admin Registration
- JWT Login
- Password Encryption (BCrypt)
- User Validation API

---

## User Service

- Automatic Profile Creation
- Kafka Consumer

---

## Banking

- Automatic Account Creation
- Deposit Money
- Withdraw Money
- Freeze Account
- Unfreeze Account
- View Account Details
- Transaction History

---

## Loan Management

- Apply Loan
- Loan Approval
- Loan Rejection
- EMI Calculation
- Loan Approval Event
- Loan Rejection Event

---

## Notification

- Welcome Email
- Loan Approval Email
- Loan Rejection Email

---

## Event-Driven Communication

### User Registration Flow

```
Auth Service
      │
      ▼
Kafka
      │
      ├────────────► User Service
      │                 │
      │                 ▼
      │          Create Profile
      │
      └────────────► Transaction Service
                        │
                        ▼
                 Create Bank Account
```

---

### Loan Approval Flow

```
Admin
    │
Approve Loan
    │
Loan Service
    │
Kafka
    ▼
Transaction Service
    │
Credit Loan Amount
    │
Save Transaction
    ▼
Notification Service
    │
Loan Approval Email
```

---

# 🔐 Security

- Spring Security
- JWT Authentication
- BCrypt Password Encryption
- Stateless Authentication
- Role-Based Users (USER / ADMIN)

---

# 🐳 Docker

Entire application is containerized.

Current containers include:

- MySQL
- Kafka
- Kafka UI
- Eureka Server
- Auth Service
- User Service
- Transaction Service
- Loan Service
- Notification Service
- API Gateway

---

# 📂 Project Structure

```
FinGuard-Enterprise/

Backend/
│
├── api-gateway
├── eureka-server
├── auth-service
├── user-service
├── transaction-service
├── loan-service
└── notification-service

docker-compose.yml
README.md
```

---

# 🛠 APIs Implemented

### Authentication

- Register User
- Register Admin
- Login

### Account

- Deposit
- Withdraw
- Freeze Account
- Unfreeze Account
- Get Account
- Transaction History

### Loan

- Apply Loan
- Approve Loan
- Reject Loan

---

# 📊 Current Development Progress

## ✅ Completed

- Microservices Architecture
- Dockerized Deployment
- Eureka Service Discovery
- API Gateway
- Kafka Integration
- JWT Authentication
- User Registration
- Admin Registration
- Automatic Account Creation
- Loan Management
- Loan Disbursement
- Email Notifications

---

## 🚧 In Progress

- Fund Transfer
- Admin Dashboard APIs
- Dashboard Statistics
- Swagger Documentation
- Health Checks
- Spring Boot Actuator

---

## 📅 Planned Features

- OTP Login
- SMS Integration
- Payment Gateway
- UPI Module
- Credit Card Module
- Net Banking
- AI Chatbot
- Redis Caching
- Spring Cloud Config Server
- ELK Stack
- Prometheus & Grafana
- Kubernetes Deployment
- Flutter Mobile App
- React Admin Dashboard

---

# 🎯 Project Goals

This project is being built to demonstrate enterprise backend development concepts including:

- Microservices
- Event-Driven Architecture
- Distributed Systems
- Kafka Messaging
- Dockerized Deployment
- Production-Oriented Backend Design
- Banking Domain Modelling
- Enterprise Java Development

---

# 👨‍💻 Author

**Vaibhav Kadam**

Backend Developer | Java | Spring Boot | Microservices | Kafka | Docker

---

# ⭐ Repository Status

> This project is actively under development. New enterprise-grade features and production enhancements are continuously being added.
