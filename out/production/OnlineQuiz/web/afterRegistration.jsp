<%@ page import="javax.swing.*" %><%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/16/20
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>
<% boolean isSuccess = (boolean) request.getAttribute("successfullyRegister"); %>
<% if (isSuccess) { %>
<h3>You have registered successfully, click <a href="./login.jsp">here</a> to login.</h3>
<% } else {%>
<h3>Unsuccessful registration, this username is already existed, click <a href="./register.jsp">here</a> to register again.</h3>
<%}%>
</body>
</html>
