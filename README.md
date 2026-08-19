# Library Management System

A beginner-friendly Java Full Stack portfolio project built with **Spring Boot 3**, **React**, **Spring Data JPA**, **Hibernate**, and **MySQL**.

---

## 📌 Project Overview

The **Library Management System** is a complete web application designed for librarians to efficiently manage authors, books, library members, and borrowing/return transactions with real-time book availability tracking and dashboard statistics.

---

## 🚀 Tech Stack

### Backend
- **Java 17 / 22**
- **Spring Boot 3.3.2**
- **Spring Data JPA & Hibernate**
- **Jakarta Validation**
- **Maven**

### Frontend
- **React 19**
- **Vite**
- **JavaScript (ES6+)**
- **Bootstrap 5 & Bootstrap Icons**
- **Axios**
- **React Router v7**

### Database & Tools
- **MySQL 8 / 9**
- **MySQL Workbench**
- **Git & GitHub**

---

## 🏗️ Architecture

```
React (Frontend)
   │
   ▼  Axios (HTTP / JSON)
Spring Boot REST API (Backend)
   │
   ├── Controllers   (REST API Endpoints)
   ├── Services      (Business Logic & Rules)
   ├── Repositories  (Spring Data JPA)
   └── Entities      (JPA Mappings)
   │
   ▼  Hibernate ORM
MySQL 8 Database
```

---

## ✨ Features

1. **Dashboard**: Real-time stats for Total Books, Total Members, Authors, Available Books, Issued Books, and Recent Borrowing Transactions.
2. **Book Management**: Full CRUD (Add, View, Edit, Delete) with ISBN uniqueness validation & search by title, ISBN, or category.
3. **Author Management**: Add, View, Edit, Delete authors with check preventing deletion if books exist under that author.
4. **Member Management**: Add, View, Edit, Delete library members with unique email validation and search.
5. **Issue Book**: Select member, select available book (auto-filtered), pick due date. Automatically decreases available quantity.
6. **Return Book**: Process book returns. Automatically updates return date, transaction status, and increments available book quantity.
7. **Borrowing History**: Filter transactions by All, Active (Issued), or Returned states.

---

## 🛠️ REST API Endpoints

### Dashboard
- `GET /api/dashboard/stats` - Get real-time stats & recent borrowings

### Books
- `GET /api/books` - Get all books
- `GET /api/books/{id}` - Get book by ID
- `POST /api/books` - Add new book
- `PUT /api/books/{id}` - Update book
- `DELETE /api/books/{id}` - Delete book
- `GET /api/books/search?keyword=value` - Search books

### Authors
- `GET /api/authors` - Get all authors
- `GET /api/authors/{id}` - Get author by ID
- `POST /api/authors` - Add new author
- `PUT /api/authors/{id}` - Update author
- `DELETE /api/authors/{id}` - Delete author
- `GET /api/authors/search?keyword=value` - Search authors

### Members
- `GET /api/members` - Get all members
- `GET /api/members/{id}` - Get member by ID
- `POST /api/members` - Add new member
- `PUT /api/members/{id}` - Update member
- `DELETE /api/members/{id}` - Delete member
- `GET /api/members/search?keyword=value` - Search members

### Borrowings
- `GET /api/borrowings` - Get all borrowing transactions
- `GET /api/borrowings/active` - Get active (ISSUED) transactions
- `GET /api/borrowings/member/{memberId}` - Member borrow history
- `POST /api/borrowings/issue` - Issue a book
- `PUT /api/borrowings/return/{id}` - Return a book

---

## 💻 Local Setup Instructions

### 1. Database Setup
1. Open MySQL Workbench.
2. Execute `database/schema.sql` to create `library_db` and tables.
3. Execute `database/sample-data.sql` to insert sample records.

### 2. Backend Setup
1. Navigate to the `backend/` directory.
2. Configure your MySQL credentials in `src/main/resources/application.properties` or environment variables:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/library_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   Backend will start on `http://localhost:8080`.

### 3. Frontend Setup
1. Navigate to the `frontend/` directory.
2. Install dependencies:
   ```bash
   npm install
   ```
3. Run dev server:
   ```bash
   npm run dev
   ```
   Frontend will start on `http://localhost:5173`.

---

## 🧪 Testing

Run backend unit tests:
```bash
cd backend
mvn test
```

---

## 📁 Repository Structure

```
library-management-system/
├── backend/
│   ├── src/main/java/com/library/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── exception/
│   │   ├── repository/
│   │   └── service/
│   ├── src/main/resources/application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── index.html
│   └── package.json
├── database/
│   ├── schema.sql
│   └── sample-data.sql
├── .gitignore
└── README.md
```
