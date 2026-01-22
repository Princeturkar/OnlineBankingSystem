-- Create database
CREATE DATABASE IF NOT EXISTS banking_db;
USE banking_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    account_no INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    balance DECIMAL(15,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create transactions table
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_no INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    description VARCHAR(255),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_no) REFERENCES users(account_no) ON DELETE CASCADE
);

-- Add indexes for better performance
CREATE INDEX idx_account_date ON transactions(account_no, transaction_date);
CREATE INDEX idx_transaction_type ON transactions(transaction_type);
CREATE INDEX idx_email ON users(email);

-- Sample user (optional)
-- INSERT INTO users (username, email, password, phone, balance) 
-- VALUES ('John Doe', 'john@example.com', 'password123', '1234567890', 1000.00);