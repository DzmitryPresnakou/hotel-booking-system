<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
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
            <c:choose>
                <c:when test="${requestScope.isDeleted}">
                    <div class="list-group-item">
                        <c:if test="${requestScope.isDeleted}">
                            <div>
                                <c:set var="deletedRoom" value="${requestScope.deletedRoom}"/>
                                <span><h5><fmt:message key="page.rooms.delete.message"/></h5></span>
                                <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                                    <div class="media-body order-2 order-lg-1">
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.number"/>: ${deletedRoom.id}</p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.occupancy"/>: ${deletedRoom.occupancy}</p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.room.class"/>: ${fn:toLowerCase(deletedRoom.roomClassDto.comfortClass)}</p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message

                                                key="page.rooms.room.price"/>:
                                            <fmt:setLocale value="en_US"/>
                                            <fmt:formatNumber value="${deletedRoom.roomClassDto.pricePerDay}"
                                                              type="currency"/>
                                        </p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.room.status"/>: ${fn:toLowerCase(deletedRoom.roomStatusDto.roomStatusEnum)}</p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.hotel"/>: ${deletedRoom.hotelDto.name}</p>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                    </div>
                </c:when>
                <c:otherwise>
                    <ul class="list-group shadow">
                        <c:forEach var="room" items="${requestScope.rooms}">
                            <li class="list-group-item">
                                <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                                    <div class="media-body order-2 order-lg-1">
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.number"/>: ${room.id}</p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.occupancy"/>: ${room.occupancy}</p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.room.class"/>: ${fn:toLowerCase(room.roomClassDto.comfortClass)}</p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.room.price"/>:
                                            <fmt:formatNumber value="${room.roomClassDto.pricePerDay * requestScope.currencyRate}" type="currency"/>
                                        </p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.room.status"/>: ${fn:toLowerCase(room.roomStatusDto.roomStatusEnum)}</p>
                                        <p class="mt-0 font-weight-bold mb-2"><fmt:message
                                                key="page.rooms.hotel"/>: ${room.hotelDto.name}</p>
                                        <c:if test="${requestScope.isAdmin && room.roomStatusDto.roomStatusEnum.equals(requestScope.availableRoom)}">
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
                                        </c:if>
                                        <c:if test="${!requestScope.isAdmin && room.roomStatusDto.roomStatusEnum.equals(requestScope.availableRoom)}">
                                            <c:url value="${pageContext.request.contextPath}book-room" var="bookURL">
                                                <c:param name="id" value="${room.id}"/>
                                            </c:url>
                                            <a href="${bookURL}">
                                                <button type="button" class="btn btn-success"><fmt:message
                                                        key="page.rooms.book.button"/></button>
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                    <c:if test="${requestScope.isAdmin}">
                        <div class="list-group-item">
                            <c:url value="${pageContext.request.contextPath}add-room" var="inputURL"/>
                            <a href="${inputURL}">
                                <button type="button" class="btn btn-info"><fmt:message
                                        key="page.rooms.add.room.ref"/></button>
                            </a>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>