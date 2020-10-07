<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 10/4/20
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>

<table class="table">
    <thead>
    <tr>
        <th scope="col">
            Question
        </th>
        <th scope="col">
            Correct answer
        </th>
        <th scope="col">
            Your answer
        </th>
        <th>
            Score
        </th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${detail}" var="elem">
           <tr>
               <td>${elem.quizDes}</td>
               <td>${elem.correctAns}</td>
               <td>${elem.studentAns}</td>
               <td>${elem.score}</td>
           </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
