<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/15/20
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<link rel="stylesheet" href="static/login-register-style.css">
<main>
    <form action="${pageContext.request.contextPath}/Register" method="post">
        <h4>Registration Form</h4>
        <div>
            <label for="username"><b>Username</b></label>
            <input type="text" name="username" id="username" required>
        </div>
        <div>
            <label for="password"><b>Password</b></label>
            <input type="password" name="password" id="password" required>
        </div>
        <div>
            <label for="email"><b>Email</b></label>
            <input type="text" name="username" id="email" required>
        </div>
        <div>
            <label for="option"><b>User type</b></label>
            <select name="option" id="option">
                <option value="teacher" selected> Teacher
                </option>
                <option value="student">Student</option>
            </select>
        </div>

        <input type="submit" value="Register">
        Already have an account? Log in <a href="./login.jsp">here. </a>
    </form>
</main>
</body>
</html>
