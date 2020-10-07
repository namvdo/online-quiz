<%@ page import="bean.QuizBean" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/19/20
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    session = request.getSession();
    QuizBean quiz = (QuizBean) session.getAttribute("quiz");
    out.println("Quiz no. " + session.getAttribute("currentQuizIdx") + 1);
    out.println(quiz.getQuizDescription());
%>
<%  List<QuizBean> quizzes = (List<QuizBean>)session.getAttribute("quizzes");
    if((int) session.getAttribute("currentQuizIdx") != quizzes.size() - 1) {%>
<a href="${pageContext.request.contextPath}/TakeQuiz?next=next">Next</a>
<%}%>
<% if((int) session.getAttribute("currentQuizIdx") != 0) {%>
<a href="${pageContext.request.contextPath}/TakeQuiz?prev=prev">Previous</a>
<%}%>
Hello World
</body>
</html>
