<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <%@include file="header.jsp"%>
    <title><fmt:message key="page.orders.title"/></title>
</head>
<body>
    <h1><fmt:message key="page.orders.title"/></h1>
    <ul>
        <c:forEach var="order" items="${requestScope.orders}">
            <li>
                <a href="${pageContext.request.contextPath}/orders?roomId=${order.id}">${order.roomId}</a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>