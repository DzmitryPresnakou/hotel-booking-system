<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.profile.title"/></title>
</head>
<body>
<div class="container py-5">
    <div class="row text-center mb-5">
        <div class="col-lg-7 mx-auto">
            <h1 class="display-4"><fmt:message key="page.profile.title"/></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <div class="list-group-item">

                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                        key="page.profile.user.firstName"/>: ${requestScope.user.firstName}</p>
                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                        key="page.profile.user.lastName"/>: ${requestScope.user.lastName}</p>
                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                        key="page.profile.user.email"/>: ${requestScope.user.email}</p>
                <p class="mt-0 font-weight-bold mb-2"><fmt:message
                        key="page.profile.user.password"/>: ${requestScope.user.password}</p>

                <c:url value="${pageContext.request.contextPath}save-profile" var="saveURL">
                    <c:param name="id" value="${requestScope.user.id}"/>
                </c:url>
                <a href="${saveURL}">
                    <button type="button" class="btn btn-info"><fmt:message
                            key="page.rooms.edit.button"/></button>
                </a>

                <c:if test="${not empty requestScope.errors}">
                    <div style="color: red">
                        <c:forEach var="error" items="${requestScope.errors}">
                            <span><fmt:message key="${error.code}"/></span>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>