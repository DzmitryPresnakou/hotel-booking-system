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
    <title><fmt:message key="page.orders.title"/></title>
</head>
<body>
<div class="container py-5">
    <div class="row text-center mb-5">
        <div class="col-lg-7 mx-auto">
            <h1 class="display-4"><fmt:message key="page.orders.title"/></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <ul class="list-group shadow">
                <c:forEach var="order" items="${requestScope.orders}">
                    <li class="list-group-item">
                        <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                            <div class="media-body order-2 order-lg-1">
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.user"/>: ${order.userDto.email}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.room.description"/></p>

                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.room.hotel.name"/>: ${order.roomCompleteDto.hotelDto.name}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.room.occupancy"/>: ${order.roomCompleteDto.occupancy}</p>

                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.room.check.in.date"/>: ${order.checkInDate}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.room.check.out.date"/>: ${order.checkOutDate}</p>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</body>
</html>