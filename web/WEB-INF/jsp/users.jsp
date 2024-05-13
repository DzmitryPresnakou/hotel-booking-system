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
    <title><fmt:message key="page.users.title"/></title>
</head>
<body>
<div class="container py-5">
    <div class="row text-center mb-5">
        <div class="col-lg-7 mx-auto">
            <h1 class="display-4"><fmt:message key="page.users.title"/></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <c:choose>
                <c:when test="${requestScope.isDeleted}">
                    <div class="list-group-item">
                        <div>
                            <span><h5><fmt:message key="page.users.delete.message"/> ${requestScope.message}</h5></span>
                        </div>
                        <c:url value="/users" var="inputURL">
                            <%-- <c:param name="id" value="${user.id}"/>--%>
                        </c:url>
                        <a href="${inputURL}">
                            <button type="button" class="btn btn-info"><fmt:message key="page.users.show.users.ref"/></button>
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <ul class="list-group shadow">
                        <c:forEach var="user" items="${requestScope.users}">
                            <c:if test="${user.isActive}">
                                <li class="list-group-item">
                                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                                        <div class="media-body order-2 order-lg-1">
                                            <p class="mt-0 font-weight-bold mb-2"><fmt:message key="page.users.email"/>: ${user.email}</p>
                                            <p class="mt-0 font-weight-bold mb-2"><fmt:message key="page.users.first.name"/>: ${user.firstName}</p>
                                            <p class="mt-0 font-weight-bold mb-2"><fmt:message key="page.users.last.name"/>: ${user.lastName}</p>
                                            <p class="mt-0 font-weight-bold mb-2"><fmt:message key="page.users.first.role"/>: ${fn:toLowerCase(user.userRoleDto.userRoleEnum)}</p>

                                            <c:url var="deleteUrl" value="${pageContext.request.contextPath}users/delete">
                                                <c:param name="id" value="${user.id}"/>
                                            </c:url>
                                            <a href="${deleteUrl}">
                                                <button type="button" class="btn btn-danger"><fmt:message key="page.users.delete.button"/></button>
                                            </a>
                                            <c:url value="${pageContext.request.contextPath}save-user" var="saveURL">
                                                <c:param name="id" value="${user.id}"/>
                                            </c:url>
                                            <a href="${saveURL}">
                                                <button type="button" class="btn btn-info"><fmt:message key="page.users.edit.button"/></button>
                                            </a>
                                        </div>
                                    </div>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>