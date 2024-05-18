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
    <title><fmt:message key="page.book-room.title"/></title>
</head>
<body>
<form action="${pageContext.request.contextPath}book-room" method="post">

    <input type="hidden" name="id" id="roomId" value="${requestScope.room.id}">

    <p class="mt-0 font-weight-bold mb-2"><fmt:message
            key="page.book-room.hotel"/>: ${room.hotelDto.name}</p>

    <p class="mt-0 font-weight-bold mb-2"><fmt:message
            key="page.book-room.occupancy"/>: ${requestScope.room.occupancy}</p>

    <p class="mt-0 font-weight-bold mb-2"><fmt:message
            key="page.book-room.room.class"/>: ${fn:toLowerCase(room.roomClassDto.comfortClass)}</p>

    <p class="mt-0 font-weight-bold mb-2"><fmt:message
            key="page.book-room.room.price"/>:
        <fmt:setLocale value="en_US"/>
        <fmt:formatNumber value="${room.roomClassDto.pricePerDay}" type="currency"/>
    </p>

    <label class="mt-0 font-weight-bold mb-2" for="checkInDateId"><fmt:message key="page.book-room.check.in.date"/>:
        <input type="date" name="checkInDate" id="checkInDateId">
    </label><br>

    <label class="mt-0 font-weight-bold mb-2" for="checkOutDateId"><fmt:message key="page.book-room.check.out.date"/>:
        <input type="date" name="checkOutDate" id="checkOutDateId">
    </label><br>

    <button type="submit"><fmt:message key="page.book-room.submit.button"/></button>

    <%--    <c:if test="${not empty requestScope.message}">--%>
    <%--        <span><h5 style="color: red">${requestScope.message} <fmt:message--%>
    <%--                key="page.registration.error.message"/></h5></span>--%>
    <%--    </c:if>--%>

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