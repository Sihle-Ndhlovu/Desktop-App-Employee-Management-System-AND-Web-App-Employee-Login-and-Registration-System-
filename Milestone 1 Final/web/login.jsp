<%-- 
    Document   : login
    Created on : 03 Mar 2025, 17:52:22
    Author     : maxib
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel ="stylesheet" href="CSS/loginStyle.css">
    </head>
    <body>

        <div class="login-container">
            <h1>Login</h1>
            <form Name="Login" action="LoginServlet" method="POST">
                <input type="text" name="txtID" placeholder="EmployeeID" required>
                <input type="password" name="pwd" placeholder="Password" required>
                <input type="submit" value="Login" name="btnlogin">

                <% if (request.getAttribute("error") != null) {%>
                <p style="color: red;"><%= request.getAttribute("error")%></p>
                <% }%>
            </form>
        </div> 

    </body>
</html>
