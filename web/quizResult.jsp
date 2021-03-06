<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/27/20
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>
<c:if test="${rejected == true}">
    <p class="text-center">Your test has been rejected.</p>
</c:if>
<c:choose>
    <c:when test="${score >= 5}">
        <div id="quiz-result container text-center mt-10">
            <p class="text-center">Your score: ${score} (${score * 10}) - Passed</p>
        </div>
    </c:when>
    <c:otherwise>
        <div class="quiz-result container text-center mt-10">
            <p class="text-center">Your score: ${score} (${score * 10}%) - Not pass</p>
        </div>
    </c:otherwise>
</c:choose>

<form action="${pageContext.request.contextPath}/Retake" id="retake" class="text-center">
    <p>Take another test?</p>
    <input type="hidden" name="retake" value="retake">
    <input type="submit" value="Start">
</form>

</body>
</html>
