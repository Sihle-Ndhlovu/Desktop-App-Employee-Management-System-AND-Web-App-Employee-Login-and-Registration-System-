<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel ="stylesheet" href="CSS/register.css">
</head>
<body>
    <div class="register-container">
    <h2>Welcome</h2>
    <h3> Please will you enter your info down below</3>
    <form action="RegisterServlet" method="POST">
        <label for="employeeId">Employee ID:</label>
        <input type="text" name="employeeId" required>
        
        <label for="fullName">Full Name:</label>
        <input type="text" name="fullName" required>
        
        <label for="department">Department:</label>
        <input type="text" name="department" required>
        
        <label for="role">Role:</label>
        <input type="text" name="role" required>
        
        <label for="password">Password:</label>
        <input type="password" name="password" required>
        
        <label for="phone">Phone:</label>
        <input type="text" name="phone" required>
        
        <label for="email">Email:</label>
        <input type="email" name="email" required>
        
        <input type="submit" value="Register">
        
        <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>
    </form>
    </div>
</body>
</html>