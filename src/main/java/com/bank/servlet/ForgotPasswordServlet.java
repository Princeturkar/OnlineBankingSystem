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

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String email = request.getParameter("email");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/banking_db", "root", "prince07"
            );

            if ("fetchQuestion".equals(action)) {
                String sql = "SELECT security_question FROM users WHERE email=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String question = rs.getString("security_question");
                    if (question == null || question.isEmpty()) {
                        request.setAttribute("error", "No security question set for this account.");
                    } else {
                        request.setAttribute("question", question);
                        request.setAttribute("email", email);
                    }
                } else {
                    request.setAttribute("error", "Email not found.");
                }
            } else if ("resetPassword".equals(action)) {
                String answer = request.getParameter("answer");
                String newPassword = request.getParameter("newPassword");

                String sql = "UPDATE users SET password=? WHERE email=? AND security_answer=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, newPassword);
                stmt.setString(2, email);
                stmt.setString(3, answer);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    request.setAttribute("success", "Password reset successful!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("error", "Incorrect answer or session expired.");
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Server Error: " + e.getMessage());
        }
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }
}
