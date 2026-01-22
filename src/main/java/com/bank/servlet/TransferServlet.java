package com.bank.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer fromAccount = (Integer) session.getAttribute("account");
        
        if (fromAccount == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int toAccount = Integer.parseInt(request.getParameter("toAccount"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        
        if (amount <= 0) {
            request.setAttribute("error", "Amount must be greater than 0!");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }
        
        if (fromAccount == toAccount) {
            request.setAttribute("error", "Cannot transfer to same account!");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }
        
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/banking_db", "root", "prince07");
            
            conn.setAutoCommit(false);
            
            // Check sender balance
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT balance FROM users WHERE account_no = ?");
            checkStmt.setInt(1, fromAccount);
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                request.setAttribute("error", "Account not found!");
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
                return;
            }
            
            double senderBalance = rs.getDouble("balance");
            if (senderBalance < amount) {
                request.setAttribute("error", "Insufficient balance!");
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
                return;
            }
            
            // Check receiver exists
            PreparedStatement receiverStmt = conn.prepareStatement(
                "SELECT username FROM users WHERE account_no = ?");
            receiverStmt.setInt(1, toAccount);
            ResultSet receiverRs = receiverStmt.executeQuery();
            
            if (!receiverRs.next()) {
                conn.rollback();
                request.setAttribute("error", "Receiver account " + toAccount + " not found!");
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
                return;
            }
            
            // Deduct from sender
            PreparedStatement deductStmt = conn.prepareStatement(
                "UPDATE users SET balance = balance - ? WHERE account_no = ?");
            deductStmt.setDouble(1, amount);
            deductStmt.setInt(2, fromAccount);
            deductStmt.executeUpdate();
            
            // Add to receiver
            PreparedStatement addStmt = conn.prepareStatement(
                "UPDATE users SET balance = balance + ? WHERE account_no = ?");
            addStmt.setDouble(1, amount);
            addStmt.setInt(2, toAccount);
            addStmt.executeUpdate();
            
            conn.commit();
            
            // Update session balance
            PreparedStatement balanceStmt = conn.prepareStatement(
                "SELECT balance FROM users WHERE account_no = ?");
            balanceStmt.setInt(1, fromAccount);
            ResultSet balanceRs = balanceStmt.executeQuery();
            if (balanceRs.next()) {
                session.setAttribute("balance", balanceRs.getDouble("balance"));
            }
            
            request.setAttribute("success", "Transfer successful! Amount: $" + amount);
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException se) {}
            e.printStackTrace();
            request.setAttribute("error", "Transfer failed: " + e.getMessage());
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {}
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.getRequestDispatcher("transfer.jsp").forward(request, response);
    }
}