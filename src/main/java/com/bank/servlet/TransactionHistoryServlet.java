package com.bank.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/transactions")
public class TransactionHistoryServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer accountNo = (Integer) session.getAttribute("account");
        String role = (String) session.getAttribute("role");
        
        if (accountNo == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if ("admin".equals(role)) {
            response.sendRedirect("adminDashboard");
            return;
        }
        
        List<Map<String, Object>> transactions = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/banking_db", "root", "prince07");
            
            String sql = "SELECT * FROM transactions WHERE account_no = ? ORDER BY transaction_date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountNo);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> transaction = new HashMap<>();
                transaction.put("date", rs.getTimestamp("transaction_date"));
                transaction.put("type", rs.getString("transaction_type"));
                transaction.put("amount", rs.getDouble("amount"));
                transaction.put("description", rs.getString("description"));
                transactions.add(transaction);
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load transactions: " + e.getMessage());
        }
        
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("transactions.jsp").forward(request, response);
    }
}