<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.add-room.title"/></title>
</head>
<body>
<form action="${pageContext.request.contextPath}save-room" method="post">

    <input type="hidden" name="id" id="roomId" value="${requestScope.room.id}">

    <label for="occupancyId"><fmt:message key="page.add-room.occupancy"/>:
        <input type="text" name="occupancy" id="occupancyId" value="${requestScope.room.occupancy}">
    </label><br>

    <label for="roomClassId"><fmt:message key="page.add-room.room.class"/>:
        <select name="roomClass" id="roomClassId">
            <c:forEach var="roomClass" items="${requestScope.roomClasses}">
                <option value="${roomClass}">${roomClass}</option>
            </c:forEach>
        </select>
    </label><br>

    <label for="roomStatusId"><fmt:message key="page.add-room.room.status"/>:
        <select name="roomStatus" id="roomStatusId">
            <c:forEach var="roomStatus" items="${requestScope.roomStatuses}">
                <option value="${roomStatus}">${roomStatus}</option>
            </c:forEach>
        </select>
    </label><br>

    <label for="hotelId"><fmt:message key="page.add-room.hotel"/>:
        <select name="hotel" id="hotelId">
            <c:forEach var="hotel" items="${requestScope.hotels}">
                <option value="${hotel.name}">${hotel.name}</option>
            </c:forEach>
        </select>
    </label><br>

    <button type="submit"><fmt:message key="page.add-room.submit.button"/></button>

    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>