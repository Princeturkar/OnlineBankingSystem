package com.bank.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
    
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
            request.setAttribute("error", "Amount must be greater than 0!");
            request.getRequestDispatcher("deposit.jsp").forward(request, response);
            return;
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/banking_db", "root", "prince07");
            
            // Update balance
            String updateSql = "UPDATE users SET balance = balance + ? WHERE account_no = ?";
            PreparedStatement stmt = conn.prepareStatement(updateSql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountNo);
            stmt.executeUpdate();
            
            // Log transaction
            String logSql = "INSERT INTO transactions (account_no, transaction_type, amount, description) VALUES (?, ?, ?, ?)";
            PreparedStatement logStmt = conn.prepareStatement(logSql);
            logStmt.setInt(1, accountNo);
            logStmt.setString(2, "DEPOSIT");
            logStmt.setDouble(3, amount);
            logStmt.setString(4, "Cash deposit");
            logStmt.executeUpdate();
            
            // Get updated balance
            String balanceSql = "SELECT balance FROM users WHERE account_no = ?";
            PreparedStatement balanceStmt = conn.prepareStatement(balanceSql);
            balanceStmt.setInt(1, accountNo);
            ResultSet rs = balanceStmt.executeQuery();
            
            if (rs.next()) {
                session.setAttribute("balance", rs.getDouble("balance"));
            }
            
            conn.close();
            request.setAttribute("success", "Deposit successful! Amount: $" + amount);
            request.getRequestDispatcher("deposit.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Transaction failed: " + e.getMessage());
            request.getRequestDispatcher("deposit.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.getRequestDispatcher("deposit.jsp").forward(request, response);
    }
}
