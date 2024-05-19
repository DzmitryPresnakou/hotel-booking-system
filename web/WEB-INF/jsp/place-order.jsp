<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <title><fmt:message key="page.place-order.title"/></title>
</head>
<body>
<div class="list-group-item">
    <form action="${pageContext.request.contextPath}place-order" method="post">

<%--        <input type="hidden" name="id" id="roomId" value="${requestScope.room.id}">--%>

        <p class="mt-0 font-weight-bold mb-2"><fmt:message key="page.place-order.hotel"/>: ${requestScope.room.hotelDto.name}</p>
        <p class="mt-0 font-weight-bold mb-2"><fmt:message key="page.place-order.number"/>: ${requestScope.room.id}</p>

        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                key="page.place-order.occupancy"/>: ${requestScope.room.occupancy}</p>

        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                key="page.place-order.room.class"/>: ${fn:toLowerCase(requestScope.room.roomClassDto.comfortClass)}</p>

        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                key="page.place-order.check.in.date"/>: ${requestScope.order.checkInDate}</p>
        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                key="page.place-order.check.out.date"/>: ${requestScope.order.checkOutDate}</p>

        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                key="page.place-order.room.price"/>:
            <fmt:setLocale value="en_US"/>
            <fmt:formatNumber value="${requestScope.room.roomClassDto.pricePerDay * requestScope.period}" type="currency"/>
        </p>

        <button type="submit" class="btn btn-danger" name="order" value="ru_RU"><fmt:message key="page.place-order.submit.button"/></button>
        <button type="submit" class="btn btn-success" name="cancel" value="en_US"><fmt:message key="page.place-order.cancel.button"/></button>

        <c:url value="/rooms" var="inputURL">
        </c:url>
        <a href="${inputURL}">
            <button type="button" class="btn btn-info"><fmt:message
                    key="page.rooms.show.rooms.ref"/></button>
        </a>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <span>${error.message}</span>
                </c:forEach>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>