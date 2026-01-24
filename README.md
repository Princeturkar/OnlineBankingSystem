# 🏦 Online Banking System (Java Servlet & JSP)

A robust, full-stack web application for digital banking built using **Java Servlets**, **JSP**, and **MySQL**. This project demonstrates core banking logic, security auditing, and advanced administrative controls.

## 🚀 Key Features

### 1. User Authentication & Security
*   **Role-Based Access Control**: Separate dashboards for **Regular Users** and **Administrative Staff**.
*   **Secret Admin Registration**: Admins must provide a "Secret Authorization Code" (`ADMIN123`) to register, preventing unauthorized access.
*   **Security Questions**: Multi-factor password recovery using customizable security questions.
*   **Login Audit Logs**: Real-time tracking of every login attempt, capturing **Email**, **IP Address**, **Timestamp**, and **Status** (SUCCESS/FAILED/BLOCKED).
*   **Role Badges**: Clear UI indicators (**USER** or **ADMIN**) on all dashboards to identify the active session role.

### 2. Banking Operations (User Role)
*   **Real-time Balance**: Instant updates to account balance after every transaction.
*   **Deposit & Withdrawal**: Automated transaction logging for all cash movements.
*   **Internal Transfers**: Securely send money to other users with automatic logging of `TRANSFER_IN` and `TRANSFER_OUT` events.
*   **PDF Statement Export**: Generate and download professional bank statements in PDF format using the **iText library**.
*   **Profile Personalization**: Upload and update profile pictures directly from the dashboard.

### 3. Administrative Control (Admin Role)
*   **User Management**: A comprehensive list of all customers and their account details.
*   **Global Account Search**: Find any user instantly by searching for their **Name** or **Email**.
*   **Account Freezing**: Capability to "Freeze" or "Unfreeze" any user account to block unauthorized access or suspicious activity.
*   **Security Audit View**: Access to recent system-wide login logs to monitor for threats.
*   **Operation Restrictions**: Admins are strictly restricted from personal banking operations (No deposit/withdraw/transfer) to ensure professional integrity.

---

## 🛠️ Technical Stack
*   **Backend**: Java 17 (Jakarta Servlet API 6.0)
*   **Frontend**: JSP, CSS3 (Responsive Design with Role Badges)
*   **Database**: MySQL 9.0+
*   **Build Tool**: Maven
*   **Libraries**: 
    *   `mysql-connector-j`: Database connectivity.
    *   `itextpdf`: PDF generation.

---

## 📂 Project Structure
- `src/main/java/com/bank/servlet`: Contains all business logic and security filters.
- `src/main/webapp`: Contains JSPs, CSS, and the `uploads` directory for profile photos.
- `README.md`: Project documentation and feature list.

## 📝 Database Schema
1.  **users**: Stores credentials, balance, roles (`user`/`admin`), status (`active`/`frozen`), and security questions.
2.  **transactions**: Detailed history of all fund movements.
3.  **login_logs**: Security audit trail for all authentication attempts.
