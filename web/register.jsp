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
    <form action="${pageContext.request.contextPath}/Register" method="post" id="login">
        <h4 class="text-center mt-5">Registration Form</h4>
        <div class="form-group col-lg-5 mx-auto">
            <label for="username" class="mx-auto">Username</label>
            <input type="text" id="username" name="username" class="form-control" aria-describedby="userHelp" placeholder="Enter the username...">
        </div>
        <div class="form-group col-lg-5 mx-auto">
            <label for="exampleInputPassword1" class="mx-auto">Password</label>
            <input type="password" class="form-control" name="password" id="exampleInputPassword1" placeholder="Password">
        </div>
        <div class="form-group col-lg-5 mx-auto">
            <label for="email" class="mx-auto">Email</label>
            <input type="text" id="email" name="email" class="form-control" aria-describedby="userHelp" placeholder="Enter the username...">
        </div>
        <div class="text-center">
        <select name="option" id="option" style="margin-left:14em">
            <option value="teacher" selected> Teacher
            </option>
            <option value="student">Student</option>
        </select>
        </div>
        <div class="col-md-12 text-center">
            <button type="submit" class="btn btn-primary text-center">Register</button>
        </div>
    </form>
    <div class="text-center p-t-46 p-b-20">
    <span class="txt2">
        Already have an account? Click <a class="form-recovery" href="./login.jsp">here</a> to log in.
    </span>
    </div>
</main>
</body>
</html>
