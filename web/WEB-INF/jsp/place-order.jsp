<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.place-order.title"/></title>
</head>
<body>
<div class="container py-5">
    <div class="row text-center mb-5">
        <div class="col-lg-7 mx-auto">
            <h1 class="display-4"><fmt:message key="page.place-order.title"/></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <div class="list-group-item">
                <form action="${pageContext.request.contextPath}place-order" method="post">

                    <p class="mt-0 font-weight-bold mb-2"><fmt:message
                            key="page.place-order.hotel"/>: ${requestScope.room.hotelDto.name}</p>
                    <p class="mt-0 font-weight-bold mb-2"><fmt:message
                            key="page.place-order.number"/>: ${requestScope.room.id}</p>

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
                        <fmt:formatNumber value="${requestScope.room.roomClassDto.pricePerDay * requestScope.period}"
                                          type="currency"/>
                    </p>


                    <c:url value="${pageContext.request.contextPath}place-order/pay" var="payURL">
                        <c:param name="roomId" value="${requestScope.room.id}"/>
                        <c:param name="orderId" value="${requestScope.order.id}"/>
                    </c:url>
                    <a href="${payURL}">
                        <button type="button" class="btn btn-success"><fmt:message
                                key="page.place-order.submit.button"/></button>
                    </a>

                    <c:url value="${pageContext.request.contextPath}place-order/cancel" var="cancelURL">
                        <c:param name="roomId" value="${requestScope.room.id}"/>
                        <c:param name="orderId" value="${requestScope.order.id}"/>
                    </c:url>
                    <a href="${cancelURL}">
                        <button type="button" class="btn btn-danger"><fmt:message
                                key="page.place-order.cancel.button"/></button>
                    </a>

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
        </div>
    </div>
</div>
</body>
</html>