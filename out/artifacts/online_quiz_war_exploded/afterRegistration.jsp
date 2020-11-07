<%@ page import="javax.swing.*" %><%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/16/20
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp"%>
<c:choose>
<c:when test="${successfullyRegister == true}">
    <p class="text-center">You have registered successfully, click <a href="login.jsp">here</a>  to log in.</p>
</c:when>
<c:otherwise>
    <p class="text-center">Unsuccessful registration. Please re-register.</p>
</c:otherwise>
</c:choose>
</body>
</html>
