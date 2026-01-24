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
    <title>SecureBank - Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="navbar">
        <div class="brand">SecureBank</div>
        <div style="display: flex; align-items: center;">
            <span class="user-info">Welcome, <%= session.getAttribute("name") %></span>
            <span style="background: #e9ecef; color: #495057; padding: 2px 8px; border-radius: 4px; font-size: 11px; font-weight: bold; margin-right: 15px; text-transform: uppercase;">
                <%= session.getAttribute("role") %>
            </span>
            <a href="logout" class="logout-btn">Logout</a>
        </div>
    </div>
    
    <div class="dashboard-container">
        <div class="dashboard-content">
            <div class="profile-section" style="text-align: center; margin-bottom: 20px;">
                <img src="uploads/<%= session.getAttribute("profile_picture") != null ? session.getAttribute("profile_picture") : "default_profile.png" %>" 
                     alt="Profile" style="width: 100px; height: 100px; border-radius: 50%; object-fit: cover; border: 2px solid #ddd;">
                <form action="uploadProfile" method="post" enctype="multipart/form-data" style="margin-top: 10px;">
                    <input type="file" name="profilePic" accept="image/*" required style="font-size: 12px;">
                    <button type="submit" class="btn" style="padding: 5px 10px; font-size: 12px;">Update Photo</button>
                </form>
            </div>
            
            <div class="balance-card">
                <h2>Account Balance</h2>
                <div class="balance-amount">$<%= String.format("%.2f", (Double)session.getAttribute("balance")) %></div>
                <p style="opacity: 0.8; margin-top: 0.5rem;">Available Balance</p>
            </div>
            
            <div class="actions">
                <div class="action-card">
                    <h3>💰 Deposit Money</h3>
                    <p>Add funds to your account securely</p>
                    <a href="deposit" class="btn">Deposit Now</a>
                </div>
                
                <div class="action-card">
                    <h3>💳 Withdraw Money</h3>
                    <p>Withdraw funds from your account</p>
                    <a href="withdraw" class="btn">Withdraw Now</a>
                </div>
                
                <div class="action-card">
                    <h3>🔄 Transfer Money</h3>
                    <p>Send money to another account instantly</p>
                    <a href="transfer" class="btn">Transfer Now</a>
                </div>
                
                <div class="action-card">
                    <h3>📊 Transaction History</h3>
                    <p>View your recent transactions</p>
                    <a href="transactions" class="btn">View History</a>
                </div>
            </div>
            
            <div class="account-info">
                <h3>Account Information</h3>
                <p><strong>Account Holder:</strong> <span><%= session.getAttribute("name") %></span></p>
                <p><strong>Email Address:</strong> <span><%= session.getAttribute("email") %></span></p>
                <p><strong>Account Number:</strong> <span><%= session.getAttribute("account") %></span></p>
                <p><strong>Account Status:</strong> <span style="color: #28a745; font-weight: 600;">✓ Active</span></p>
            </div>
        </div>
    </div>
</body>
</html>
