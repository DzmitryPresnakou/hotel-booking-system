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
                                            key="page.orders.room.occupancy"/>: ${order.roomCompleteDto.occupancy}</p>

<%--                                    <fmt:formatDate pattern = "yyyy-MM-dd"--%>
<%--                                                    value = "${order.checkInDate}"/>--%>

<%--                                    <fmt:formatDate pattern = "yyyy-MM-dd"--%>
<%--                                                    value = "${order.checkOutDate}"/>--%>
<%--                                    <fmt:formatDate value="${order.checkInDate}"--%>
<%--                                                    pattern="yyyy-MM-dd"--%>
<%--                                                    var="checkInDate"/>--%>

<%--                                    <fmt:formatDate value="${order.checkOutDate}"--%>
<%--                                                    pattern="yyyy-MM-dd"--%>
<%--                                                    var="checkOutDate"/>--%>

                                    <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                            key="page.orders.room.check.in.date"/>: ${order.checkInDate}</p>
                                    <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                            key="page.orders.room.check.out.date"/>: ${order.checkOutDate}</p>

<%--                                    <c:url var="deleteUrl"--%>
<%--                                           value="${pageContext.request.contextPath}orders/delete">--%>
<%--                                        <c:param name="id" value="${user.id}"/>--%>
<%--                                    </c:url>--%>
<%--                                    <a href="${deleteUrl}">--%>
<%--                                        <button type="button" class="btn btn-danger"><fmt:message--%>
<%--                                                key="page.orders.delete.button"/></button>--%>
<%--                                    </a>--%>
<%--                                    <c:url value="${pageContext.request.contextPath}save-order" var="saveURL">--%>
<%--                                        <c:param name="id" value="${order.id}"/>--%>
<%--                                    </c:url>--%>
<%--                                    <a href="${saveURL}">--%>
<%--                                        <button type="button" class="btn btn-info"><fmt:message--%>
<%--                                                key="page.orders.edit.button"/></button>--%>
<%--                                    </a>--%>

                                </div>
                            </div>
                        </li>

                </c:forEach>
            </ul>


        </div>
    </div>


<%--    <h1><fmt:message key="page.orders.title"/></h1>--%>
<%--    <ul>--%>
<%--        <c:forEach var="order" items="${requestScope.orders}">--%>
<%--            <li>--%>
<%--                <a href="${pageContext.request.contextPath}/orders?roomId=${order.id}">${order.roomId}</a>--%>
<%--            </li>--%>
<%--        </c:forEach>--%>
<%--    </ul>--%>
</body>
</html>