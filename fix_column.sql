-- Run this in MySQL to fix the column issue
USE banking_db;

-- Check what columns exist
DESCRIBE users;

-- If you see 'id' instead of 'account_no', run this:
ALTER TABLE users CHANGE id account_no INT AUTO_INCREMENT;

-- If no primary key exists, add account_no:
-- ALTER TABLE users ADD COLUMN account_no INT AUTO_INCREMENT PRIMARY KEY FIRST;