<%-- 
    Document   : dashboard
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <link rel ="stylesheet" href="CSS/dashboard.css">
    </head>
    <body>
        <h1>Employee Dashboard</h1>
        <h2>Welcome, <%= session.getAttribute("employeeName") %>!</h2>
        <a href="login.jsp"><button class="logout-btn">Log out</button></a>
        
    </body>
</html>
