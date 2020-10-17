<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 9/19/20
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>
<table class="table">
    <thead>
    <tr>
        <th scope="col">
            No.
        </th>
        <th scope="col">
            Quiz Description
        </th>
        <th scope="col">
            Created date
        </th>
    </tr>
    </thead>
    <tbody>
    <c:set var="idx" value="0"/>
       <c:forEach items="${quizzesWithOffset}" var="item">
           <tr>
               <td>${startIndex + idx}</td>
               <td>${item.quizDescription}</td>
               <td><fmt:formatDate value="${item.createdAt}" type="date"/></td>
               <c:set var="idx" value="${idx + 1}"/>
           </tr>
       </c:forEach>
    </tbody>
</table>
<c:forEach begin="1" end="${pages}" step="1" var="page">
    <a href="ManageQuiz?page=${page}">${page}</a>
</c:forEach>
</div>
</div>
</body>
</html>
