<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SecureBank - Create Account</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body style="display: flex; align-items: center; justify-content: center; min-height: 100vh;">
    <div class="container">
        <div class="page-header">
            <h2>🏦 SecureBank</h2>
            <p>Join thousands of satisfied customers</p>
        </div>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        
        <form action="register" method="post">
            <div class="form-group">
                <label for="username">👤 Full Name</label>
                <input type="text" id="username" name="username" placeholder="Enter your full name" required>
            </div>
            
            <div class="form-group">
                <label for="email">📧 Email Address</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>
            </div>
            
            <div class="form-group">
                <label for="phone">📱 Phone Number</label>
                <input type="tel" id="phone" name="phone" placeholder="Enter your phone number" required>
            </div>
            
            <div class="form-group">
                <label for="password">🔒 Password</label>
                <input type="password" id="password" name="password" placeholder="Create a strong password" required>
            </div>

            <div class="form-group">
                <label for="securityQuestion">❓ Security Question</label>
                <select id="securityQuestion" name="securityQuestion" required>
                    <option value="">Select a question</option>
                    <option value="What is your mother's maiden name?">What is your mother's maiden name?</option>
                    <option value="What was your first pet's name?">What was your first pet's name?</option>
                    <option value="In what city were you born?">In what city were you born?</option>
                </select>
            </div>

            <div class="form-group">
                <label for="securityAnswer">🔑 Security Answer</label>
                <input type="text" id="securityAnswer" name="securityAnswer" placeholder="Your answer" required>
            </div>

            <div class="form-group">
                <label for="role">🎭 Account Role</label>
                <select id="role" name="role" required onchange="toggleAdminCode()">
                    <option value="user">Regular User (Customer)</option>
                    <option value="admin">Bank Administrator (Staff)</option>
                </select>
            </div>

            <div class="form-group" id="adminCodeGroup" style="display: none;">
                <label for="adminCode">🛡️ Secret Admin Code</label>
                <input type="password" id="adminCode" name="adminCode" placeholder="Enter special authorization code">
            </div>
            
            <script>
                function toggleAdminCode() {
                    var role = document.getElementById("role").value;
                    var adminGroup = document.getElementById("adminCodeGroup");
                    adminGroup.style.display = (role === "admin") ? "block" : "none";
                }
            </script>
            
            <button type="submit" class="btn">Create Account</button>
        </form>
        
        <div class="link">
            <p>Already have an account? <a href="login">Sign In</a></p>
        </div>
    </div>
</body>
</html>
