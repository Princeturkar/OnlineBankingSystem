<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password - Online Bank</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Reset Password</h2>
        <% if(request.getAttribute("error") != null) { %>
            <p style="color:red;"><%= request.getAttribute("error") %></p>
        <% } %>
        
        <form action="forgotPassword" method="post">
            <div class="form-group">
                <label>Email Address:</label>
                <input type="email" name="email" required placeholder="Enter your registered email">
            </div>
            <button type="submit" name="action" value="fetchQuestion">Get Security Question</button>
        </form>

        <% if(request.getAttribute("question") != null) { %>
            <hr>
            <form action="forgotPassword" method="post">
                <input type="hidden" name="email" value="<%= request.getAttribute("email") %>">
                <p><strong>Question:</strong> <%= request.getAttribute("question") %></p>
                <div class="form-group">
                    <label>Your Answer:</label>
                    <input type="text" name="answer" required>
                </div>
                <div class="form-group">
                    <label>New Password:</label>
                    <input type="password" name="newPassword" required>
                </div>
                <button type="submit" name="action" value="resetPassword">Reset Password</button>
            </form>
        <% } %>
        
        <br>
        <a href="login.jsp">Back to Login</a>
    </div>
</body>
</html>
