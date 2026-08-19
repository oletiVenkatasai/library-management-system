-- ============================================================
-- Library Management System - Sample Data
-- Run this AFTER schema.sql
-- ============================================================

USE library_db;

-- ============================================================
-- Authors (5 authors)
-- ============================================================
INSERT INTO authors (name, biography) VALUES
('Robert C. Martin', 'Software engineer and author known for promoting clean code practices.'),
('Joshua Bloch', 'Former chief Java architect at Google, author of Effective Java.'),
('Kathy Sierra', 'Co-creator of the Head First series and Java certification books.'),
('Martin Fowler', 'British software developer specializing in software design and refactoring.'),
('Herbert Schildt', 'Author of numerous programming books covering Java, C, and C++.');

-- ============================================================
-- Books (10 books)
-- ============================================================
INSERT INTO books (title, isbn, category, quantity, available_quantity, published_date, author_id) VALUES
('Clean Code', '978-0132350884', 'Software Engineering', 5, 4, '2008-08-01', 1),
('The Clean Coder', '978-0137081073', 'Software Engineering', 3, 3, '2011-05-13', 1),
('Effective Java', '978-0134685991', 'Java', 4, 3, '2018-01-06', 2),
('Head First Java', '978-0596009205', 'Java', 6, 5, '2005-02-09', 3),
('Head First Design Patterns', '978-0596007126', 'Design Patterns', 3, 2, '2004-10-25', 3),
('Refactoring', '978-0134757599', 'Software Engineering', 4, 4, '2018-11-20', 4),
('Patterns of Enterprise Application Architecture', '978-0321127426', 'Architecture', 2, 2, '2002-11-15', 4),
('Java: The Complete Reference', '978-1260440232', 'Java', 5, 3, '2021-12-10', 5),
('Java: A Beginner''s Guide', '978-1260463552', 'Java', 4, 4, '2022-01-14', 5),
('Clean Architecture', '978-0134494166', 'Architecture', 3, 2, '2017-09-10', 1);

-- ============================================================
-- Members (8 members)
-- ============================================================
INSERT INTO members (name, email, phone, address, membership_date) VALUES
('Sai Kumar', 'sai.kumar@email.com', '9876543210', '12 MG Road, Bangalore', '2026-01-15'),
('Rahul Sharma', 'rahul.sharma@email.com', '9876543211', '45 Park Street, Mumbai', '2026-02-10'),
('Priya Patel', 'priya.patel@email.com', '9876543212', '78 Nehru Nagar, Delhi', '2026-03-05'),
('Ananya Reddy', 'ananya.reddy@email.com', '9876543213', '23 Jubilee Hills, Hyderabad', '2026-04-20'),
('Vikram Singh', 'vikram.singh@email.com', '9876543214', '56 Civil Lines, Jaipur', '2026-05-12'),
('Deepa Nair', 'deepa.nair@email.com', '9876543215', '89 Marine Drive, Kochi', '2026-06-08'),
('Arjun Menon', 'arjun.menon@email.com', '9876543216', '34 Anna Nagar, Chennai', '2026-07-01'),
('Kavitha Iyer', 'kavitha.iyer@email.com', '9876543217', '67 Koregaon Park, Pune', '2026-07-25');

-- ============================================================
-- Borrow Transactions (5 transactions - mix of ISSUED and RETURNED)
-- ============================================================
INSERT INTO borrow_transactions (book_id, member_id, issue_date, due_date, return_date, status) VALUES
(1, 1, '2026-08-01', '2026-08-15', NULL, 'ISSUED'),
(3, 2, '2026-07-28', '2026-08-11', '2026-08-10', 'RETURNED'),
(4, 3, '2026-08-05', '2026-08-19', NULL, 'ISSUED'),
(5, 4, '2026-07-20', '2026-08-03', '2026-08-01', 'RETURNED'),
(8, 5, '2026-08-10', '2026-08-24', NULL, 'ISSUED');
