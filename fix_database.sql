-- Fix database structure - check current table structure first
USE banking_db;

-- Show current table structure
DESCRIBE users;

-- If the primary key is 'id' instead of 'account_no', run this:
-- ALTER TABLE users CHANGE id account_no INT AUTO_INCREMENT;

-- Or if you need to add account_no column:
-- ALTER TABLE users ADD COLUMN account_no INT AUTO_INCREMENT PRIMARY KEY FIRST;

-- Alternative: Create tables with correct structure
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    account_no INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    balance DECIMAL(15,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_no INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    description VARCHAR(255),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_no) REFERENCES users(account_no) ON DELETE CASCADE
);

-- Add indexes
CREATE INDEX idx_account_date ON transactions(account_no, transaction_date);
CREATE INDEX idx_transaction_type ON transactions(transaction_type);
CREATE INDEX idx_email ON users(email);