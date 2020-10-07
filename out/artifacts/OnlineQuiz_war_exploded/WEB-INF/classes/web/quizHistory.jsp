<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 10/4/20
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>
<div class="container">
<table class="table">
   <thead>
        <tr>
            <th scope="col">
                No.
            </th>
            <th scope="col">
                Taken time
            </th>
            <th scope="col">
                Score
            </th>
        </tr>
   </thead>
   <tbody>
        <c:set var="count" scope="request" value="1"/>
        <c:forEach items="${quizHistory}" var="q">
            <tr>
                <td>${count}</td>
                <td>${q.takenTime}</td>
                <td>${q.score} <br> <a href="${pageContext.request.contextPath}/ClusterDetail?clusterId=${q.clusterId}">View detail</a></td>
            </tr>
            <c:set var="count" value="${count + 1}"/>
        </c:forEach>
   </tbody>
</table>
</div>
</body>
</html>
