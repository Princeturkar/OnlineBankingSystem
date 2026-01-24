<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SecureBank - Transaction History</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="navbar">
        <div class="brand">SecureBank</div>
        <div style="display: flex; align-items: center;">
            <span class="user-info">Welcome, <%= session.getAttribute("name") %></span>
            <a href="logout" class="logout-btn">Logout</a>
        </div>
    </div>
    
    <div class="dashboard-container">
        <div class="dashboard-content">
            <div class="page-header" style="display: flex; justify-content: space-between; align-items: center;">
                <div>
                    <h2>📊 Transaction History</h2>
                    <p>View your recent banking transactions</p>
                </div>
                <a href="downloadStatement" class="btn" style="background: #28a745; text-decoration: none;">📄 Download PDF</a>
            </div>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>
            
            <% 
            List<Map<String, Object>> transactions = (List<Map<String, Object>>) request.getAttribute("transactions");
            if (transactions != null && !transactions.isEmpty()) {
            %>
                <div style="background: white; border-radius: 20px; overflow: hidden; box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);">
                    <table class="transaction-table">
                        <thead>
                            <tr>
                                <th>📅 Date & Time</th>
                                <th>💼 Type</th>
                                <th>💰 Amount</th>
                                <th>📝 Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Map<String, Object> transaction : transactions) { %>
                                <tr>
                                    <td><%= transaction.get("date") %></td>
                                    <td>
                                        <span style="padding: 0.3rem 0.8rem; border-radius: 15px; font-size: 0.8rem; font-weight: 600;
                                            <% String type = transaction.get("type").toString(); %>
                                            <% if (type.equals("DEPOSIT") || type.equals("TRANSFER_IN")) { %>
                                                background: #d4edda; color: #155724;
                                            <% } else { %>
                                                background: #f8d7da; color: #721c24;
                                            <% } %>">
                                            <%= type.replace("_", " ") %>
                                        </span>
                                    </td>
                                    <td class="<%= transaction.get("type").toString().contains("OUT") || transaction.get("type").equals("WITHDRAW") ? "debit" : "credit" %>">
                                        $<%= String.format("%.2f", (Double) transaction.get("amount")) %>
                                    </td>
                                    <td><%= transaction.get("description") %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <div style="background: white; padding: 3rem; border-radius: 20px; text-align: center; box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);">
                    <h3 style="color: #666; margin-bottom: 1rem;">📊 No Transactions Found</h3>
                    <p style="color: #888;">You haven't made any transactions yet. Start by making a deposit or transfer!</p>
                </div>
            <% } %>
            
            <div class="links">
                <a href="dashboard.jsp">Back to Dashboard</a>
            </div>
        </div>
    </div>
</body>
</html>