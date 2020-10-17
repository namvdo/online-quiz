<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/15/20
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
    <div class="container text-center pt-5">
        <h4>Welcome, <p style="color:blue;display:inline">${user}</p></h4>
        <h5>Explore over ${allQuizzes}+ quizzes available now and test your knowledge!</h5>
        <h5>Take a practice now and immediately get the result!</h5>
        <h5>Enter the number of questions: </h5>
        <form action="${pageContext.request.contextPath}/QuizInitializer">
            <label>
                <input type="text" name="numOfQuizzes" style="height: 38px;" required>
            </label>
            <input type="submit" value="Start" class="btn btn-success">
        </form>
        <c:if test="${invalidInput == true}">
            <p style="color:red">Invalid input</p>
        </c:if>
    </div>
<script src="removeTime.js"></script>

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