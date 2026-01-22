-- Check and fix database structure
USE banking_db;

-- Check current structure
SHOW TABLES;
DESCRIBE users;
DESCRIBE transactions;
:
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_no INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    description VARCHAR(255),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Test insert to verify structure works
-- INSERT INTO transactions (account_no, transaction_type, amount, description) 
-- VALUES (1, 'TEST', 100.00, 'Test transaction');