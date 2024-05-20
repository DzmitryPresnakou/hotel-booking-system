<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.book-room.title"/></title>
</head>
<body>
<div class="container py-5">
    <div class="row text-center mb-5">
        <div class="col-lg-7 mx-auto">
            <h1 class="display-4"><fmt:message key="page.book-room.title"/></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <div class="list-group-item">
                <form action="${pageContext.request.contextPath}book-room" method="post">

                    <input type="hidden" name="id" id="roomId" value="${requestScope.room.id}">

                    <p class="mt-0 font-weight-bold mb-2"><fmt:message
                            key="page.book-room.hotel"/>: ${requestScope.room.hotelDto.name}</p>
                    <p class="mt-0 font-weight-bold mb-2"><fmt:message
                            key="page.book-room.number"/>: ${requestScope.room.id}</p>

                    <p class="mt-0 font-weight-bold mb-2"><fmt:message
                            key="page.book-room.occupancy"/>: ${requestScope.room.occupancy}</p>

                    <p class="mt-0 font-weight-bold mb-2"><fmt:message
                            key="page.book-room.room.class"/>: ${fn:toLowerCase(requestScope.room.roomClassDto.comfortClass)}</p>

                    <p class="mt-0 font-weight-bold mb-2"><fmt:message
                            key="page.book-room.room.price"/>:
                        <fmt:setLocale value="en_US"/>
                        <fmt:formatNumber value="${requestScope.room.roomClassDto.pricePerDay}" type="currency"/>
                    </p>

                    <label class="mt-0 font-weight-bold mb-2" for="checkInDateId"><fmt:message
                            key="page.book-room.check.in.date"/>:
                        <input type="date" name="checkInDate" id="checkInDateId">
                    </label><br>

                    <label class="mt-0 font-weight-bold mb-2" for="checkOutDateId"><fmt:message
                            key="page.book-room.check.out.date"/>:
                        <input type="date" name="checkOutDate" id="checkOutDateId">
                    </label><br>

                    <button type="submit"><fmt:message key="page.book-room.submit.button"/></button>

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