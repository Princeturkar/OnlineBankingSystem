<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("account") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SecureBank - Deposit Money</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="navbar">
        <div class="brand">SecureBank</div>
        <div style="display: flex; align-items: center;">
            <span class="user-info">Welcome, <%= session.getAttribute("name") %></span>
            <a href="logout" class="logout-btn">Logout</a>
        </div>
    </div>
    
    <div style="display: flex; align-items: center; justify-content: center; min-height: calc(100vh - 80px);">
        <div class="container">
            <div class="page-header">
                <h2>💰 Deposit Money</h2>
                <p>Add funds to your account securely</p>
            </div>
            
            <div style="background: linear-gradient(135deg, #e8f5e8, #f0f8f0); padding: 1.2rem; border-radius: 12px; margin-bottom: 1.5rem; border: 1px solid #c3e6cb;">
                <p style="margin: 0; color: #155724; font-weight: 600;">
                    💳 Current Balance: <span style="font-size: 1.1rem;">$<%= String.format("%.2f", (Double)session.getAttribute("balance")) %></span>
                </p>
            </div>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>
            
            <% if (request.getAttribute("success") != null) { %>
                <div class="success"><%= request.getAttribute("success") %></div>
            <% } %>
            
            <form action="deposit" method="post">
                <div class="form-group">
                    <label for="amount">💵 Deposit Amount</label>
                    <input type="number" id="amount" name="amount" step="0.01" min="0.01" placeholder="Enter amount to deposit" required>
                </div>
                
                <button type="submit" class="btn">Deposit Money</button>
                <a href="dashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
            </form>
        </div>
    </div>
</body>
</html>
