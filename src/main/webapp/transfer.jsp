<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SecureBank - Transfer Money</title>
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
    
    <div style="display: flex; align-items: center; justify-content: center; min-height: calc(100vh - 80px);">
        <div class="container">
            <div class="page-header">
                <h2>🔄 Transfer Money</h2>
                <p>Send money to another account instantly</p>
            </div>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>
            
            <% if (request.getAttribute("success") != null) { %>
                <div class="success"><%= request.getAttribute("success") %></div>
            <% } %>
            
            <form method="post" action="transfer">
                <div class="form-group">
                    <label for="toAccount">🏦 Recipient Account Number</label>
                    <input type="number" id="toAccount" name="toAccount" placeholder="Enter recipient's account number" required>
                </div>
                
                <div class="form-group">
                    <label for="amount">💵 Transfer Amount</label>
                    <input type="number" id="amount" name="amount" step="0.01" min="0.01" placeholder="Enter amount to transfer" required>
                </div>
                
                <button type="submit" class="btn">Transfer Money</button>
                <a href="dashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
            </form>
        </div>
    </div>
</body>
</html>