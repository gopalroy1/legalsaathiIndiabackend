# Legal Saathi India – Backend

A scalable backend system built with **Spring Boot** and **PostgreSQL** for a legal and taxation service platform. This backend powers features like user onboarding, service catalog, orders, and transaction handling – all structured to support future payment gateway integration and admin dashboards.

---

## 🚀 Tech Stack

* **Java 17+**
* **Spring Boot 3.5**
* **PostgreSQL**
* **Lombok**
* **Spring Data JPA**
* **HikariCP**
* **Maven**
* (Optional: **Razorpay**, **JWT**, **Swagger** for extensions)

---

## 📁 Project Structure

```
src/
│
├── models/            # JPA Entities (User, Product, Order, Transaction, Enums)
├── repositories/      # Spring Data JPA interfaces
├── controllers/       # REST API controllers (TBD)
├── services/          # Business logic layer (TBD)
├── dto/               # Request/Response DTOs (TBD)
├── config/            # Security, CORS, and App config (TBD)
└── application.yaml   # DB & environment configuration
```

---

## 📌 Core Features Implemented

### ✅ User System

* `UserModel` with fields:

    * Email, phone, password, city, address, etc.
    * Enum `UserRole` (ADMIN, USER, etc.)
    * Soft delete (`isDeleted`)
    * Timestamping (`createdAt`, `updatedAt`)

### ✅ Product Catalog

* `ProductModel`:

    * Title, description, price, discountPrice
    * Enum `ProductCategory` (e.g., GST, Audit)
    * Flags: `isActive`, `isDeleted`
    * Created/updated timestamps

### ✅ Order Management

* `OrderModel`:

    * Links to `userId`, `productId`
    * Stores product snapshot (title + price)
    * Enum `OrderStatus` (PENDING, PAID, CANCELLED, COMPLETED)
    * Soft delete + timestamps

### ✅ Transaction System

* `TransactionModel`:

    * Gateway-agnostic
    * Supports manual/admin-created transactions
    * Fields: amount, externalTransactionId, paymentMode, status
    * Metadata JSON (for webhook dump or invoice notes)
    * Soft delete + audit timestamps

---

## 💄 Database

* **PostgreSQL** used as relational DB
* All entities mapped via **JPA annotations**
* Auto-creation of tables via Hibernate
* Use `\dt` in psql to list created tables

---

## 💡 Development Tips

### 🛠️ Common Commands

```bash
# Run locally
mvn spring-boot:run

# Build the JAR
mvn clean install

# Connect to DB (Mac/Linux)
psql -d legal_saathi_india
```

### ✅ PostgreSQL Setup

```bash
# Start DB service (Mac)
brew services start postgresql@14

# Create DB
createdb legal_saathi_india

# Connect
psql -d legal_saathi_india
```

---

## 🔒 Future Plans

* [ ] JWT-based auth system
* [ ] Admin panel endpoints
* [ ] Razorpay or Stripe payment flow
* [ ] Order + transaction dashboard
* [ ] Swagger API docs
* [ ] Email notification system (optional)

---

## 📂 Notes to Self

* All models use soft deletes → always filter `isDeleted = false`
* Timestamps are handled via `@PrePersist`/`@PreUpdate`
* No `@ManyToOne` mappings – using IDs directly to reduce complexity
* All enums (roles, status, category) are string-based for DB clarity

---

## ✍️ Author

**Gopal Roy**
Building full-stack, scalable, Java-based systems
*This repo is part of the Legal Saathi India platform.*
