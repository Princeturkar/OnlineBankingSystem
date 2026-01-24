package com.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer accountNo = (Integer) session.getAttribute("account");

        if (accountNo == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        double amount = Double.parseDouble(request.getParameter("amount"));

        if (amount <= 0) {
            request.setAttribute("error", "Amount must be greater than 0");
            request.getRequestDispatcher("withdraw.jsp").forward(request, response);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/banking_db",
                "root",
                "prince07"
            );

            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT balance FROM users WHERE account_no=?"
            );
            checkStmt.setInt(1, accountNo);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                conn.close();
                response.sendRedirect("login.jsp");
                return;
            }

            double balance = rs.getDouble("balance");

            if (balance < amount) {
                request.setAttribute("error", "Insufficient balance. Current balance: $" + String.format("%.2f", balance));
                request.getRequestDispatcher("withdraw.jsp").forward(request, response);
                conn.close();
                return;
            }

            PreparedStatement updateStmt = conn.prepareStatement(
                "UPDATE users SET balance = balance - ? WHERE account_no=?"
            );
            updateStmt.setDouble(1, amount);
            updateStmt.setInt(2, accountNo);
            updateStmt.executeUpdate();

            // Log transaction
            PreparedStatement logStmt = conn.prepareStatement(
                "INSERT INTO transactions (account_no, transaction_type, amount, description) VALUES (?, ?, ?, ?)"
            );
            logStmt.setInt(1, accountNo);
            logStmt.setString(2, "WITHDRAW");
            logStmt.setDouble(3, amount);
            logStmt.setString(4, "Cash withdrawal");
            logStmt.executeUpdate();

            conn.close();
            
            session.setAttribute("balance", balance - amount);
            request.setAttribute("success", "Withdrawal successful! Amount: $" + String.format("%.2f", amount));
            request.getRequestDispatcher("withdraw.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Transaction failed: " + e.getMessage());
            request.getRequestDispatcher("withdraw.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if (session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if ("admin".equals(role)) {
            response.sendRedirect("adminDashboard");
            return;
        }

        request.getRequestDispatcher("withdraw.jsp").forward(request, response);
    }
}
