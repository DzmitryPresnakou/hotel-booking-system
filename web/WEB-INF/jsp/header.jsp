<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="${pageContext.request.contextPath}css/style.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript"
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<div class="container">
    <nav class="navbar align-content-center navbar-expand-lg navbar-light bg-light">

        <fmt:setLocale
                value="${sessionScope.lang != null ? sessionScope.lang : (param.lang != null ? param.lang : 'en_US')}"/>
        <fmt:setBundle basename="translations"/>
        <div class="p-1">
            <c:if test="${not empty sessionScope.user}">
                <form action="${pageContext.request.contextPath}/logout" method="post">

<%--                        ${fn:toLowerCase(order.orderStatusDto.orderStatusEnum).equals('open')}--%>

                    <c:if test="${fn:toLowerCase(sessionScope.user.userRoleDto.userRoleEnum).equals('admin')}">
                    <c:url value="/users" var="usersURL">
                    </c:url>
                    <a href="${usersURL}">
                        <button type="button" class="btn btn-light"><fmt:message
                                key="page.header.users"/></button>
                    </a>
                    </c:if>

                    <c:url value="/rooms" var="roomsURL">
                    </c:url>
                    <a href="${roomsURL}">
                        <button type="button" class="btn btn-light"><fmt:message
                                key="page.header.rooms"/></button>
                    </a>

                    <c:url value="/orders" var="ordersURL">
                    </c:url>
                    <a href="${ordersURL}">
                        <button type="button" class="btn btn-light"><fmt:message
                                key="page.header.orders"/></button>
                    </a>

                    <c:url value="/profile" var="profileURL">
                    </c:url>
                    <a href="${profileURL}">
                        <button type="button" class="btn btn-light"><fmt:message
                                key="page.header.profile"/></button>
                    </a>

                    <button type="submit" class="btn btn-light"><fmt:message key="page.header.logout.button"/></button>
                </form>
            </c:if>
        </div>
        <div class="p-1">
            <form action="${pageContext.request.contextPath}/locale" method="post">
                <button type="submit" class="btn btn-light" name="lang" value="ru_RU">РУС</button>
                <button type="submit" class="btn btn-light" name="lang" value="en_US">ENG</button>

            </form>
        </div>
    </nav>
</div>