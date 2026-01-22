-- Insert a test user
USE banking_db;

INSERT INTO users (username, password, email, phone, balance) 
VALUES ('Test User', 'test123', 'test@example.com', '1234567890', 1000.00);

-- Check if user was created
SELECT * FROM users;