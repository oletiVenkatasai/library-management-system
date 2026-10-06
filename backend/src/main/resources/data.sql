-- =============================================
-- Library Management System - Sample Data
-- Auto-loaded by Spring Boot on startup (H2)
-- =============================================

-- Authors
INSERT INTO authors (name, biography) VALUES
('George Orwell', 'Eric Arthur Blair, known by his pen name George Orwell, was an English novelist, essayist, journalist and critic.'),
('J.K. Rowling', 'British author, best known for the Harry Potter fantasy series.'),
('Robert C. Martin', 'American software engineer and author, known as Uncle Bob.'),
('Yuval Noah Harari', 'Israeli public intellectual, historian and professor at the Hebrew University of Jerusalem.'),
('Frank Herbert', 'American science fiction author best known for the novel Dune.');

-- Books
INSERT INTO books (title, isbn, category, quantity, available_quantity, published_date, author_id) VALUES
('1984', '978-0451524935', 'Dystopian Fiction', 5, 5, '1949-06-08', 1),
('Animal Farm', '978-0451526342', 'Political Satire', 3, 3, '1945-08-17', 1),
('Harry Potter and the Philosopher''s Stone', '978-0439708180', 'Fantasy', 8, 8, '1997-06-26', 2),
('Harry Potter and the Chamber of Secrets', '978-0439064873', 'Fantasy', 6, 6, '1998-07-02', 2),
('Clean Code', '978-0132350884', 'Software Engineering', 4, 4, '2008-08-01', 3),
('The Clean Coder', '978-0137081073', 'Software Engineering', 3, 3, '2011-05-13', 3),
('Sapiens: A Brief History of Humankind', '978-0062316097', 'History', 5, 5, '2011-01-01', 4),
('Homo Deus: A Brief History of Tomorrow', '978-0062464316', 'Non-Fiction', 4, 4, '2015-09-10', 4),
('Dune', '978-0441013593', 'Science Fiction', 6, 6, '1965-08-01', 5),
('Dune Messiah', '978-0441172696', 'Science Fiction', 4, 4, '1969-01-01', 5);

-- Members
INSERT INTO members (name, email, phone, address, membership_date) VALUES
('Alice Johnson', 'alice.johnson@email.com', '+91-9876543210', '123 Main Street, Mumbai', '2024-01-15'),
('Bob Smith', 'bob.smith@email.com', '+91-9876543211', '456 Park Avenue, Delhi', '2024-02-20'),
('Carol Williams', 'carol.williams@email.com', '+91-9876543212', '789 Oak Lane, Bangalore', '2024-03-10'),
('David Brown', 'david.brown@email.com', '+91-9876543213', '321 Elm Street, Chennai', '2024-04-05'),
('Eva Martinez', 'eva.martinez@email.com', '+91-9876543214', '654 Maple Drive, Hyderabad', '2024-05-12');
