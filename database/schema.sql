-- ============================================================
-- Library Management System - Database Schema
-- Database: library_db
-- ============================================================

-- Create database (if it doesn't already exist)
CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- ============================================================
-- Table: authors
-- Stores author information
-- ============================================================
CREATE TABLE IF NOT EXISTS authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    biography TEXT
);

-- ============================================================
-- Table: books
-- Stores book information with a foreign key to authors
-- ============================================================
CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    category VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    available_quantity INT NOT NULL DEFAULT 0,
    published_date DATE,
    author_id BIGINT NOT NULL,

    CONSTRAINT fk_book_author
        FOREIGN KEY (author_id) REFERENCES authors(id)
);

-- ============================================================
-- Table: members
-- Stores library member information
-- ============================================================
CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    membership_date DATE NOT NULL
);

-- ============================================================
-- Table: borrow_transactions
-- Tracks book borrowing and returning
-- Status: 'ISSUED' or 'RETURNED'
-- ============================================================
CREATE TABLE IF NOT EXISTS borrow_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'ISSUED',

    CONSTRAINT fk_borrow_book
        FOREIGN KEY (book_id) REFERENCES books(id),

    CONSTRAINT fk_borrow_member
        FOREIGN KEY (member_id) REFERENCES members(id)
);
