<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/15/20
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<h4>Welcome, <%=session.getAttribute("user")%></h4>
<h5>Enter the number of questions: </h5>
<form action="${pageContext.request.contextPath}/TakeQuiz">
    <label>
        <input type="text" name="numOfQuizzes">
    </label>
    <input type="submit" value="Start">
</form>
