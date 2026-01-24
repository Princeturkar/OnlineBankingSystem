package com.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if (!"admin".equals(role)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_db", "root", "prince07");

            // Fetch Users with optional search
            String search = request.getParameter("search");
            String userSql = "SELECT account_no, username, email, balance, status FROM users WHERE role='user'";
            if (search != null && !search.trim().isEmpty()) {
                userSql += " AND (username LIKE ? OR email LIKE ?)";
            }
            
            PreparedStatement userStmt = conn.prepareStatement(userSql);
            if (search != null && !search.trim().isEmpty()) {
                userStmt.setString(1, "%" + search + "%");
                userStmt.setString(2, "%" + search + "%");
            }
            
            ResultSet rsUsers = userStmt.executeQuery();
            request.setAttribute("userList", rsUsers);

            // Fetch Recent Logs
            String logSql = "SELECT * FROM login_logs ORDER BY login_time DESC LIMIT 10";
            PreparedStatement logStmt = conn.prepareStatement(logSql);
            ResultSet rsLogs = logStmt.executeQuery();
            request.setAttribute("logList", rsLogs);

            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
