<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - SecureBank</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #f4f4f4; }
        .status-active { color: green; font-weight: bold; }
        .status-frozen { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <div class="container" style="max-width: 1000px;">
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <h2>🏦 Admin Control Panel</h2>
            <div>
                <span style="background: #dc3545; color: white; padding: 4px 12px; border-radius: 4px; font-size: 12px; font-weight: bold; text-transform: uppercase; margin-right: 15px;">
                    ADMIN
                </span>
                <a href="logout">Logout</a>
            </div>
        </div>
        
        <h3>User Management</h3>
        <form action="adminDashboard" method="get" style="margin-bottom: 20px;">
            <input type="text" name="search" placeholder="Search by name or email..." value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>" style="padding: 8px; width: 300px;">
            <button type="submit" class="btn" style="padding: 8px 15px;">🔍 Search</button>
            <% if(request.getParameter("search") != null) { %>
                <a href="adminDashboard" style="margin-left: 10px; font-size: 14px;">Clear Search</a>
            <% } %>
        </form>
        <table>
            <tr>
                <th>Acc No</th>
                <th>Name</th>
                <th>Email</th>
                <th>Balance</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <% 
                ResultSet users = (ResultSet) request.getAttribute("userList");
                while(users != null && users.next()) { 
            %>
            <tr>
                <td><%= users.getInt("account_no") %></td>
                <td><%= users.getString("username") %></td>
                <td><%= users.getString("email") %></td>
                <td>$<%= users.getDouble("balance") %></td>
                <td><span class="status-<%= users.getString("status") %>"><%= users.getString("status") %></span></td>
                <td>
                    <form action="updateUserStatus" method="post" style="display:inline;">
                        <input type="hidden" name="account_no" value="<%= users.getInt("account_no") %>">
                        <input type="hidden" name="new_status" value="<%= users.getString("status").equals("active") ? "frozen" : "active" %>">
                        <button type="submit"><%= users.getString("status").equals("active") ? "Freeze" : "Unfreeze" %></button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>

        <h3 style="margin-top: 40px;">Recent Login Activity</h3>
        <table>
            <tr>
                <th>Email</th>
                <th>Time</th>
                <th>IP Address</th>
                <th>Status</th>
            </tr>
            <% 
                ResultSet logs = (ResultSet) request.getAttribute("logList");
                while(logs != null && logs.next()) { 
            %>
            <tr>
                <td><%= logs.getString("email") %></td>
                <td><%= logs.getTimestamp("login_time") %></td>
                <td><%= logs.getString("ip_address") %></td>
                <td><%= logs.getString("status") %></td>
            </tr>
            <% } %>
        </table>
    </div>
</body>
</html>
