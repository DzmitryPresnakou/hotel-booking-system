<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}css/style.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.add-room.title"/></title>
</head>
<body>
<div class="list-group-item">
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

        <c:url value="/rooms" var="inputURL">
        </c:url>
        <a href="${inputURL}">
            <button type="button" class="btn btn-info"><fmt:message
                    key="page.rooms.show.rooms.ref"/></button>
        </a>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <span><fmt:message key="${error.code}"/></span>
                </c:forEach>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>