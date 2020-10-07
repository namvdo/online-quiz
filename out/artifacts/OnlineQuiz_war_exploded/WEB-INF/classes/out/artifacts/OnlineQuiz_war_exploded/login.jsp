<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/15/20
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<link rel="stylesheet" href="static/login-register-style.css">
<form action="${pageContext.request.contextPath}/Login" method="post">
    <h4>Login Form</h4>
    <div>
        <label for="username"><b>Username</b></label>
        <input type="text" name="username" id="username" required>
    </div>
    <div>
        <label for="password"><b>Password</b></label>
        <input type="password" name="password" id="password" required>
    </div>
    <input type="submit" value="Login">
    <a href="./register.jsp">Register</a>

</form>
<% if (request.getAttribute("loginFailed") != null && (boolean) request.getAttribute("loginFailed")) {
    out.println("<p style=color:red>Incorrect username or password. </p>");
}
%>
</body>
</html>
