-- Create transactions table for logging all banking transactions
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_no INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description VARCHAR(255),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_no) REFERENCES users(account_no)
);

-- Add indexes for better performance
CREATE INDEX idx_account_date ON transactions(account_no, transaction_date);
CREATE INDEX idx_transaction_type ON transactions(transaction_type);

-- Sample data (optional)
-- INSERT INTO transactions (account_no, transaction_type, amount, description) 
-- VALUES (1001, 'DEPOSIT', 1000.00, 'Initial deposit');