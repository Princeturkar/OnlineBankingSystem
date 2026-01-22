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
        
        if (accountNo == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // For now, show empty transactions since table doesn't exist
        List<Map<String, Object>> transactions = new ArrayList<>();
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("transactions.jsp").forward(request, response);
    }
}