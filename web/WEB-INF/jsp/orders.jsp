<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
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
                                        key="page.orders.room.number"/>: ${order.roomCompleteDto.id}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.room.occupancy"/>: ${order.roomCompleteDto.occupancy}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.room.check.in.date"/>: ${order.checkInDate}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.room.check.out.date"/>: ${order.checkOutDate}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.orders.room.status"/>: ${fn:toLowerCase(order.orderStatusDto.orderStatusEnum)}</p>

                                <c:if test="${fn:toLowerCase(order.orderStatusDto.orderStatusEnum).equals('open')}">
                                    <c:url value="${pageContext.request.contextPath}place-order" var="orderURL">
                                        <c:param name="id" value="${order.id}"/>
                                    </c:url>
                                    <a href="${orderURL}">
                                        <button type="button" class="btn btn-info"><fmt:message
                                                key="page.orders.order.button"/></button>
                                    </a>
                                </c:if>
                                <c:if test="${requestScope.isAdmin}">
                                    <c:url var="deleteUrl"
                                           value="${pageContext.request.contextPath}orders/delete">
                                        <c:param name="id" value="${order.id}"/>
                                    </c:url>
                                    <a href="${deleteUrl}">
                                        <button type="button" class="btn btn-danger"><fmt:message
                                                key="page.orders.delete.button"/></button>
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
</body>
</html>