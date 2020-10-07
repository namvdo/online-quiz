<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/15/20
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp" %>
<link rel="stylesheet" href="static/login-register-style.css">
<form action="${pageContext.request.contextPath}/Login" method="post" id="login">
    <h4 class="text-center mt-5">Login Form</h4>
    <div class="form-group col-lg-5 mx-auto">
        <label for="username" class="mx-auto">Username</label>
        <input type="text" id="username" name="username" class="form-control" aria-describedby="userHelp" placeholder="Enter the username...">
    </div>
    <div class="form-group col-lg-5 mx-auto">
        <label for="exampleInputPassword1" class="mx-auto">Password</label>
        <input type="password" class="form-control" name="password" id="exampleInputPassword1" placeholder="Password">
    </div>
    <div class="col-md-12 text-center">
        <button type="submit" class="btn btn-primary text-center">Login</button>
    </div>
</form>
<div class="text-center p-t-46 p-b-20">
    <span class="txt2">
        Don't have an account? Click <a class="form-recovery" href="./register.jsp">here</a> to sign up a new one.
    </span>
</div>


<% if (request.getAttribute("loginFailed") != null && (boolean) request.getAttribute("loginFailed")) {
    out.println("<p style=color:red>Incorrect username or password. </p>");
}
%>
</body>
</html>
