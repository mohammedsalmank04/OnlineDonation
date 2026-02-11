# Donation Management System

## 📌 Overview

This project is a **Donation Management System** that allows users to make donations securely through an integrated payment gateway. It supports role-based access, invoice generation, email testing, and system testing to ensure reliability and performance.

---

## 🚀 Features

* 💳 **Stripe Payment Gateway Integration** for secure payments
* 🔔 **Stripe Webhooks** to listen and respond to payment events
* 🔐 **JWT Cookie Authentication** for secure session handling
* 👥 **Role-Based Access**

    * **User** → Makes donations
    * **Admin** → Manages organizations and donation categories
* 📄 **PDF Invoice Generation** using **iText** after successful donations
* 📧 **Email Testing** using **Mailtrap SMTP server**
* 🧪 **JUnit Testing** for backend logic validation
* ⚡ **Stress Testing** to evaluate performance under load
* 🗄 **MySQL Database** for persistent storage

---

## 🧑‍💻 User Roles

### User

* Registers and logs in
* Makes donations
* Receives donation invoice

### Admin

* Creates donation organizations
* Creates donation categories
* Manages platform data

---

## 🛠 Tech Stack

| Layer          | Technology                   |
| -------------- | ---------------------------- |
| Backend        | Spring Boot                  |
| Security       | JWT Authentication           |
| Payments       | Stripe API                   |
| PDF Generation | iText                        |
| Email Testing  | Mailtrap SMTP                |
| Testing        | JUnit + Stress Testing Tools |
| Database       | MySQL                        |

---

## 🔄 Workflow

1. User logs in securely using JWT cookies
2. User selects organization and donation category
3. Payment processed via Stripe
4. Stripe webhook confirms payment event
5. System generates PDF invoice
6. Invoice can be emailed or downloaded

---

## 📦 Installation & Setup

1. Clone the repository

```
git clone <repo-url>
```

2. Configure environment variables:

* Stripe keys
* Mailtrap credentials
* Database credentials

3. Run the application

```
mvn spring-boot:run
```

---

## 🧪 Testing

Run unit tests:

```
mvn test
```

Stress testing can be performed using external load testing tools against API endpoints.

---

## 📜 License

This project is for educational and demonstration purposes.

---

## ✨ Author Notes

This project demonstrates backend development skills including secure authentication, payment integration, PDF generation, and system testing.
