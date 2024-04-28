<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Список гостиничных номеров</h1>
    <ul>
        <c:forEach var="room" items="${requestScope.rooms}">
            <li>${room.id} - ${fn:toLowerCase(room.description)}</li>
        </c:forEach>
    </ul>
</body>
</html>