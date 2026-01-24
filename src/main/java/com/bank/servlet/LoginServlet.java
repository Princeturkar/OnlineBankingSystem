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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("username"); // Form field is named username but contains email
        String password = request.getParameter("password");
        
        System.out.println("Login attempt - Email: " + email + ", Password: " + password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/banking_db",
                "root",
                "prince07"
            );

            // Debug: Check what users exist
            String sql = "SELECT account_no, username, email, balance, profile_picture, role, status FROM users WHERE email=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            System.out.println("Executing query: " + sql);

            ResultSet rs = stmt.executeQuery();
            
            String ipAddress = request.getRemoteAddr();
            String logSql = "INSERT INTO login_logs (email, ip_address, status) VALUES (?, ?, ?)";
            PreparedStatement logStmt = conn.prepareStatement(logSql);
            logStmt.setString(1, email);
            logStmt.setString(2, ipAddress);

            if (rs.next()) {
                String status = rs.getString("status");
                if ("frozen".equals(status)) {
                    logStmt.setString(3, "BLOCKED (FROZEN)");
                    logStmt.executeUpdate();
                    request.setAttribute("error", "Your account is frozen. Contact admin.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

                logStmt.setString(3, "SUCCESS");
                logStmt.executeUpdate();
                
                System.out.println("Login successful for: " + email);
                HttpSession session = request.getSession();

                session.setAttribute("account", rs.getInt("account_no"));
                session.setAttribute("name", rs.getString("username"));
                session.setAttribute("email", rs.getString("email"));
                session.setAttribute("balance", rs.getDouble("balance"));
                session.setAttribute("profile_picture", rs.getString("profile_picture"));
                session.setAttribute("role", rs.getString("role"));

                if ("admin".equals(rs.getString("role"))) {
                    response.sendRedirect("adminDashboard");
                } else {
                    response.sendRedirect("dashboard.jsp");
                }
            } else {
                logStmt.setString(3, "FAILED");
                logStmt.executeUpdate();
                
                System.out.println("Login failed for: " + email);
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

            stmt.close();
            rs.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Server error");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
