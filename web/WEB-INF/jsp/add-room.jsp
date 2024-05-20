<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.add-room.title"/></title>
</head>
<body>
<div class="container py-5">
    <div class="row text-center mb-5">
        <div class="col-lg-7 mx-auto">
            <h1 class="display-4"><fmt:message key="page.add-room.title"/></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <div class="list-group-item">
                <form action="${pageContext.request.contextPath}/add-room" method="post">
                    <label for="occupancyId"><fmt:message key="page.add-room.occupancy"/>:
                        <input type="text" class="form-control" name="occupancy" id="occupancyId">
                    </label><br>

                    <label for="roomClassId"><fmt:message key="page.add-room.room.class"/>:
                        <select name="roomClass" class="form-control" id="roomClassId">
                            <c:forEach var="roomClass" items="${requestScope.roomClasses}">
                                <option value="${roomClass}">${roomClass}</option>
                            </c:forEach>
                        </select>
                    </label><br>

                    <label for="roomStatusId"><fmt:message key="page.add-room.room.status"/>:
                        <select name="roomStatus" class="form-control" id="roomStatusId">
                            <c:forEach var="roomStatus" items="${requestScope.roomStatuses}">
                                <option value="${roomStatus}">${roomStatus}</option>
                            </c:forEach>
                        </select>
                    </label><br>

                    <label for="hotelId"><fmt:message key="page.add-room.hotel"/>:
                        <select name="hotel" class="form-control" id="hotelId">
                            <c:forEach var="hotel" items="${requestScope.hotels}">
                                <option value="${hotel.name}">${hotel.name}</option>
                            </c:forEach>
                        </select>
                    </label><br>
                    <button type="submit"><fmt:message key="page.add-room.submit.button"/></button>

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