<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/19/20
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="header.jsp" %>
<div class="containter">
<form action="${pageContext.request.contextPath}/MakeQuiz" id="make-quiz" role="form">
        <div class = "form-group">
            <label for = "question">Question</label>
            <textarea type = "text" name="quizDes" class = "form-control" rows="2" id="question" placeholder = "Enter the quiz description..."></textarea>
        </div>
        <div class = "form-group">
            <label>Option 1</label>
            <textarea id="otp1" name="opt1" class = "form-control"  placeholder = "Enter the first option"></textarea>
        </div>
        <div class = "form-group">
            <label>Option 2</label>
            <textarea id="otp2" name="opt2" class = "form-control" placeholder = "Enter the second option"></textarea>
        </div>
        <div class = "form-group">
            <label>Option 3</label>
            <textarea id="otp3" name="opt3" class = "form-control"  placeholder = "Enter the third option"></textarea>
        </div>
        <div class = "form-group">
            <label>Option 4</label>
            <textarea id="otp4" name="opt4" class = "form-control" placeholder = "Enter the fourth option"></textarea>
        </div>
        <c:forEach begin="0" end="3" var="current">
            <input value="${current}" name="answer" type="checkbox" style="width:20px;height:20px;"> Option ${current + 1}
        </c:forEach>
        <button type="submit" class="btn btn-success">Insert</button>
</form>
<c:if test="${inserted == true}">
    <p>Successful insertion.</p>
</c:if>
<c:if test="${emptyQuizDes == true}">
    <p style="color:red" class="text-center">You need providing the quiz description.</p>
</c:if>
    <c:if test="${allEmpty == true}">
        <p class="text-center" style="color:red">You need to provide answer options.</p>
    </c:if>
    <c:if test="${noQuiz < 2}">
        <p class="text-center" style="color:red">You need to provide at least 2 answer options.</p>
    </c:if>
    <c:if test="${notProvideCorrectAns == true}">
        <p class="text-center"style="color:red">You need to provide the correct answer(s).</p>
    </c:if>
</div>
</body>
</html>
