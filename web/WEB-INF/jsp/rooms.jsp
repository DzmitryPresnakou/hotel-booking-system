<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}css/products.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.rooms.title"/></title>
</head>
<body>

<div class="container py-5">
    <div class="row text-center mb-5">
        <div class="col-lg-7 mx-auto">
            <h1 class="display-4"><fmt:message key="page.rooms.title"/></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <ul class="list-group shadow">
                <c:forEach var="room" items="${requestScope.rooms}">
                    <li class="list-group-item">
                        <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                            <div class="media-body order-2 order-lg-1">
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.rooms.occupancy"/>: ${room.occupancy}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.rooms.room.class"/>: ${fn:toLowerCase(room.roomClassDto.comfortClass)}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.rooms.room.status"/>: ${fn:toLowerCase(room.roomStatusDto.roomStatusEnum)}</p>
                                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                        key="page.rooms.hotel"/>: ${fn:toLowerCase(room.hotelDto.name)}</p>
                                <c:url var="deleteUrl"
                                       value="${pageContext.request.contextPath}rooms/delete">
                                    <c:param name="id" value="${room.id}"/>
                                </c:url>
                                <a href="${deleteUrl}">
                                    <button type="button" class="btn btn-danger"><fmt:message
                                            key="page.rooms.delete.button"/></button>
                                </a>
                                <c:url value="${pageContext.request.contextPath}save-room" var="saveURL">
                                    <c:param name="id" value="${room.id}"/>
                                </c:url>
                                <a href="${saveURL}">
                                    <button type="button" class="btn btn-info"><fmt:message
                                            key="page.rooms.edit.button"/></button>
                                </a>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <div class="list-group-item">
                <c:url value="${pageContext.request.contextPath}add-room" var="inputURL"/>
                <a href="${inputURL}">
                    <button type="button" class="btn btn-info"><fmt:message key="page.rooms.add.room.ref"/></button>
                </a>
            </div>
        </div>

    </div>
</div>


<%--<h1><fmt:message key="page.rooms.title"/></h1>--%>
<%--<ul>--%>
<%--    <c:forEach var="room" items="${requestScope.rooms}">--%>
<%--        <li>${fn:toLowerCase(room.description)}</li>--%>
<%--    </c:forEach>--%>
<%--</ul>--%>


</body>
</html>