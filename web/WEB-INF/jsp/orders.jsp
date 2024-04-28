<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Заказанные номера</h1>
    <ul>
        <c:forEach var="order" items="${requestScope.orders}">
            <li>
                <a href="${pageContext.request.contextPath}/orders?roomId=${order.id}">${order.roomId}</a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>