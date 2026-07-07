# 🛡️ FinGuard Enterprise

[![Java Version](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.0-brightgreen?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-Latest-black?style=for-the-badge&logo=apachekafka&logoColor=white)](https://kafka.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](https://opensource.org/licenses/MIT)

**FinGuard Enterprise** is a high-performance, secure, and event-driven financial microservices application designed for enterprise account management, transaction processing, real-time notifications, and automated loan processing. Built with **Java 21**, **Spring Boot**, and **Apache Kafka**, the system demonstrates a robust distributed architecture leveraging database-per-service isolation, asynchronous communication choreography, and unified gateway authentication.

---

## 🏗️ Architecture Overview

The system is decomposed into highly cohesive microservices that communicate both synchronously via **Spring Cloud OpenFeign** (for validation) and asynchronously via **Apache Kafka** (for event propagation).

```mermaid
graph TD
    Client[Web/Mobile Client] -->|HTTP Request / JWT| Gateway[API Gateway :8080]
    
    %% Gateway Routing
    Gateway -->|Route /api/auth| AuthService[Auth Service :8081]
    Gateway -->|Route /api/users| UserService[User Service :8082]
    Gateway -->|Route /api/accounts| TransactionService[Transaction Service :8083]
    Gateway -->|Route /api/loans| LoanService[Loan Service :8085]
    
    %% Service Discovery
    AuthService -.->|Register| Eureka[Eureka Discovery Server :8761]
    UserService -.->|Register| Eureka
    TransactionService -.->|Register| Eureka
    LoanService -.->|Register| Eureka
    NotificationService -.->|Register| Eureka
    
    %% Databases
    AuthService --->|JDBC| AuthDB[(MySQL: finguard_auth)]
    UserService --->|JDBC| UserDB[(MySQL: finguard_user)]
    TransactionService --->|JDBC| TransDB[(MySQL: finguard_transaction)]
    LoanService --->|JDBC| LoanDB[(MySQL: finguard_loan)]
    
    %% Inter-service Sync Feign
    LoanService -->|OpenFeign Validate User| UserService
    UserService -->|OpenFeign Check Roles| AuthService
    
    %% Kafka Messaging
    AuthService ==>|Publish: UserRegisteredEvent| KafkaBroker((Apache Kafka))
    LoanService ==>|Publish: LoanApprovedEvent / LoanRejectedEvent| KafkaBroker
    
    KafkaBroker ==u=>|Consume| UserService
    KafkaBroker ==u=>|Consume| TransactionService
    KafkaBroker ==u=>|Consume| NotificationService[:8084]
```

---

## 📡 Event-Driven Choreography (Kafka)

FinGuard uses **Apache Kafka** to decouple core transaction flows, ensuring low-latency operations and reliable cross-service consistency:

| Event Name | Producer Service | Consumer Services | Business Action Triggered |
| :--- | :--- | :--- | :--- |
| `UserRegisteredEvent` | **Auth Service** | **User Service**<br>**Transaction Service**<br>**Notification Service** | • Creates user profile registry.<br>• Initializes a new active bank account.<br>• Dispatches a welcome email to the user. |
| `LoanApprovedEvent` | **Loan Service** | **Transaction Service**<br>**Notification Service** | • Automates loan disbursement (credits loan amount to account).<br>• Dispatches a loan approval email with repayment details. |
| `LoanRejectedEvent` | **Loan Service** | **Notification Service** | • Dispatches a loan rejection notification to the user. |

---

## 🗂️ Microservices Catalog

Below is a detailed inventory of the ecosystem’s microservices:

| Service Name | Port | Database | Primary Responsibility |
| :--- | :--- | :--- | :--- |
| **Eureka Server** | `8761` | *N/A* | Decentralized Service Registry & Discovery. |
| **API Gateway** | `8080` | *N/A* | Unified Entry Point, dynamic routing, and global JWT verification filter. |
| **Auth Service** | `8081` | `finguard_auth` | User registration, admin generation, JWT token issuing, and role-based permissions. |
| **User Service** | `8082` | `finguard_user` | User profiles management, KYC document validation, approval/rejection workflows. |
| **Transaction Service** | `8083` | `finguard_transaction` | Account balance tracking, deposits, withdrawals, transfers, account freezing/unfreezing. |
| **Loan Service** | `8085` | `finguard_loan` | Loan application, automated EMI calculations, loan approval/rejection workflows. |
| **Notification Service** | `8084` | *N/A* | Consumer engine sending real-time SMTP emails upon registration and loan status changes. |
| **Kafka UI** | `8088` | *N/A* | Web console for Kafka cluster monitoring, topic tracking, and message inspection. |

---

## 🔐 API Reference & Endpoints

All client requests must go through the **API Gateway (`http://localhost:8080`)**. Protected endpoints require an `Authorization: Bearer <JWT_TOKEN>` header.

### 🔑 Authentication Service (`/api/auth`)
* `POST /api/auth/register` — Registers a new customer (triggers `UserRegisteredEvent`).
* `POST /api/auth/login` — Authenticates credentials and returns a signed JWT token.
* `POST /api/auth/register-admin` — Registers an administrative user.
* `GET /api/auth/validate/{userId}` — Verifies if a user is registered and returns role payload.

### 👤 User Service (`/api/users`)
* `POST /api/users/profile` — Creates a detailed profile (addresses, phone, KYC details).
* `GET /api/users/profile/{userId}` — Retrieves user profile and KYC status.
* `PUT /api/users/profile/{userId}` — Updates profile details.
* `PUT /api/users/profile/{userId}/approve` — (Admin) Approves KYC verification status.
* `PUT /api/users/profile/{userId}/reject` — (Admin) Rejects KYC verification status.

### 💳 Transaction Service (`/api/accounts`)
* `GET /api/accounts/{userId}` — Retrieves active bank account details and current balance.
* `POST /api/accounts/deposit` — Deposits funds into the user's account.
* `POST /api/accounts/withdraw` — Withdraws funds from the user's account (validates balance limits).
* `GET /api/accounts/history/{userId}` — Fetches transaction ledger history.
* `PUT /api/accounts/{accountId}/freeze` — (Admin) Temporarily suspends account operations.
* `PUT /api/accounts/{accountId}/unfreeze` — (Admin) Restores suspended account operations.

### 📝 Loan Service (`/api/loans`)
* `POST /api/loans/apply` — Submits a loan application request.
* `GET /api/loans/{userId}` — Lists all loan applications (active, approved, or rejected) for a user.
* `PUT /api/loans/{loanId}/approve` — (Admin) Approves a loan (triggers balance credit & notifications).
* `PUT /api/loans/{loanId}/reject` — (Admin) Rejects a loan (triggers rejection notifications).

---

## ⚡ Tech Stack & Technologies

* **Language**: Java 21 (JDK 21 LTS)
* **Framework**: Spring Boot (v4.1.0-M1/LATEST)
* **Microservices Framework**: Spring Cloud (Gateway, OpenFeign, Netflix Eureka Client/Server)
* **Messaging Broker**: Apache Kafka (KRaft mode)
* **Relational Database**: MySQL 8.0
* **Build Automation**: Gradle
* **Containerization**: Docker & Docker Compose
* **Notification System**: Spring Boot Starter Mail (SMTP)
* **Security & Authentication**: Spring Security + JWT (JSON Web Tokens)

---

## 🚀 Getting Started & Local Setup

### 📋 Prerequisites
Ensure you have the following installed on your machine:
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (includes Docker Compose)
* [JDK 21](https://oracle.com/java/technologies/downloads/) (if building or running services outside of Docker)
* [Gradle](https://gradle.org/install/) (Optional - wrapper scripts are provided)

### ⚙️ Step 1: Environment Configuration
1. Clone the repository and navigate to the project directory:
   ```bash
   git clone https://github.com/Vaibhavk9921/finguard-enterprise.git
   cd finguard-enterprise
   ```
2. Create your `.env` configuration file from the provided template:
   ```bash
   cp .env.example .env
   ```
3. Open `.env` and fill in your details (e.g., SMTP details for receiving emails):
   ```env
   DB_USERNAME=root
   DB_PASSWORD=root
   JWT_SECRET=FinGuardEnterpriseSecretKeyForDevelopment123456
   
   # Setup Real SMTP (e.g., Gmail App Passwords) for Notification Service
   MAIL_USERNAME=your-email@gmail.com
   MAIL_PASSWORD=your-app-password
   ```

### 🐳 Step 2: Spin Up Infrastructure (MySQL, Kafka, Kafka UI)
Use Docker Compose to launch the database container and the Kafka messaging cluster:
```bash
docker-compose up -d mysql kafka kafka-ui
```
This automatically:
* Starts a MySQL server mapping to local port `3307`.
* Runs the `scripts/init-mysql.sql` to initialize databases: `finguard_auth`, `finguard_user`, `finguard_transaction`, and `finguard_loan`.
* Launches a KRaft Kafka broker on port `9092`.
* Exposes **Kafka UI** at `http://localhost:8088`.

### 🛠️ Step 3: Build Microservices
To compile and build all the microservices locally using Gradle:
```bash
# On Linux/macOS
./gradlew build -x test

# On Windows (PowerShell)
.\gradlew.bat build -x test
```

### 📦 Step 4: Run the Complete Microservices Suite
You can choose to spin up all services together via Docker Compose:
```bash
# Start all microservices in background
docker-compose up -d --build
```

Alternatively, you can start the services locally one by one in separate terminals:
1. **Eureka Server**:
   ```bash
   cd Backend/eureka-server && ./gradlew bootRun
   ```
2. **Auth Service**:
   ```bash
   cd Backend/auth-service && ./gradlew bootRun
   ```
3. **User Service**:
   ```bash
   cd Backend/user-service && ./gradlew bootRun
   ```
4. **Transaction Service**:
   ```bash
   cd Backend/transaction-service && ./gradlew bootRun
   ```
5. **Loan Service**:
   ```bash
   cd Backend/loan-service && ./gradlew bootRun
   ```
6. **Notification Service**:
   ```bash
   cd Backend/notification-service && ./gradlew bootRun
   ```
7. **API Gateway**:
   ```bash
   cd Backend/api-gateway && ./gradlew bootRun
   ```

Verify that all services successfully register in the Eureka dashboard at [http://localhost:8761](http://localhost:8761).

---

## 📈 Monitoring & Administration dashboards

* **Eureka Registry Dashboard**: [http://localhost:8761](http://localhost:8761) — Monitor service health and active instances.
* **Kafka UI Dashboard**: [http://localhost:8088](http://localhost:8088) — Inspect topics, message feeds, and consumer lag.

---

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:
1. Fork the Project.
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the Branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

## 📝 License

Distributed under the MIT License. See `LICENSE` for more information.

---
*Developed with ❤️ as a state-of-the-art event-driven microservices architecture.*
