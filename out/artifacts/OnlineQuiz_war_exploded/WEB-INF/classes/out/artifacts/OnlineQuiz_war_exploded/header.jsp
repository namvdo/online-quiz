<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/15/20
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <c:set var="pageTitle" scope="request" value="${param.title}"/>
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="static/style.css">
</head>
<body>
<div class="container-fullwidth">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="collapse navbar-collapse ">
            <div class="navbar-nav mx-auto">
                <a class="nav-item nav-link px-5" href="./index.jsp">Home</a>
                <c:choose>
                    <c:when test="${user ne null}">
                        <a class="nav-item nav-link px-5" href="./takeQuiz.jsp">Take quiz</a>
                    </c:when>
                    <c:otherwise>
                        <c:redirect url="${pageContext.request.contextPath}/ErrorHandler"/>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${user ne null}">
                        <a class="nav-item nav-link px-5" href="./makeQuiz.jsp">Make quiz</a>
                    </c:when>
                    <c:otherwise>
                        <c:redirect url="${pageContext.request.contextPath}/ErrorHandler"/>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${user ne null}">
                        <a class="nav-item nav-link px-5" href="${pageContext.request.contextPath}/ManageQuiz">Manage
                            quiz</a>
                    </c:when>
                    <c:otherwise>
                        <c:redirect url="${pageContext.request.contextPath}/ErrorHandler"/>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${user ne null}">
                        <a class="nav-item nav-link px-5" href="${pageContext.request.contextPath}/QuizHistory">Quiz
                            history</a>
                    </c:when>
                    <c:otherwise>
                        <c:redirect url="${pageContext.request.contextPath}/ErrorHandler"/>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${user ne null}">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/Logout">Log out</a>
                    </c:when>
                    <c:otherwise>
                        <c:redirect url="${pageContext.request.contextPath}/ErrorHandler"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</div>

