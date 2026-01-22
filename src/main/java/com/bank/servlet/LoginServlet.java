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
            String sql = "SELECT account_no, username, email, balance FROM users WHERE email=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            System.out.println("Executing query: " + sql);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful for: " + email);
                HttpSession session = request.getSession();

                session.setAttribute("account", rs.getInt("account_no"));
                session.setAttribute("name", rs.getString("username"));
                session.setAttribute("email", rs.getString("email"));
                session.setAttribute("balance", rs.getDouble("balance"));

                response.sendRedirect("dashboard.jsp");
            } else {
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
