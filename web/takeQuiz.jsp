<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="bean.QuizBean" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.AnswerBean" %>
<%@ page import="dao.AnswerDAO" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/19/20
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>

<%@ include file="header.jsp" %>
<div class="time-elapsing text-center pt-3" >
    Time remaining:
    <div id="time" style="color:blue">
        <c:if test="${formatTime != null}">
            ${formatTime}
        </c:if>
    </div>
</div>
<div class="container ml-50">
    <form action="${pageContext.request.contextPath}/TakeQuiz" id="quizPage" class="mt-5">
        <c:set var="count" value="1" scope="page"/>

        <h4>Q${currentQuizIdx + 1} - ${quiz.quizDescription}</h4>
        <c:set var="quizId" value="${quiz.quizId}"/>
        <c:forEach var="answer" items="${curAns}">
            <c:out value="${count}"/>
            <c:forEach items="${allAnsFromStudent[quizId]}" var="item">
                <c:if test="${item eq answer.answerId}">
                    <c:set var="contains" value="${item}"/>
                </c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${contains eq answer.answerId}">
                    <input type="checkbox" style="width:20px;height:20px;" name="answer" value="${answer.answerId}" class="answer-checkbox"
                           checked onclick="getChosenAnswers()"> ${answer.answerText}
                </c:when>
                <c:otherwise>
                    <input type="checkbox" style="width:20px;height:20px;" name="answer" class="answer-checkbox"
                           value="${answer.answerId}" onclick="getChosenAnswers()"> ${answer.answerText}
                </c:otherwise>
            </c:choose>
            <input type="hidden" id="quizId" value="${quiz.quizId}">
            <c:set var="count" value="${count + 1}"/>
            <br>
        </c:forEach>
        <c:if test="${currentQuizIdx > 0}">
            <input type="submit" value="Previous" name="prev">
        </c:if>
        <c:if test="${currentQuizIdx < quizzes.size() - 1}">
            <input type="submit" value="Next" name="next">
        </c:if>
            <input type="submit" name="submit" value="Submit">
    </form>
</div>
<script src="countDown.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>

</html>
