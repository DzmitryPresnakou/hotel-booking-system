<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="header.jsp"%>
    <title><fmt:message key="page.registration.title"/></title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="firstNameId"><fmt:message key="page.registration.first.name"/>:
        <input type="text" name="firstName" id="firstNameId">
    </label><br>
    <label for="lastNameId"><fmt:message key="page.registration.last.name"/>:
        <input type="text" name="lastName" id="lastNameId">
    </label><br>
    <label for="emailId"><fmt:message key="page.registration.email"/>:
        <input type="text" name="email" id="emailId">
    </label><br>
    <label for="passwordId"><fmt:message key="page.registration.password"/>:
        <input type="password" name="password" id="passwordId">
    </label><br>
    <input type="hidden" name="role" id="roleIdHidden" value="USER">
    <button type="submit"><fmt:message key="page.registration.submit.button"/></button>

    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
            </c:forEach>
        </div>
    </c:if>
</form>
<c:if test="${not empty requestScope.message}">
<span><h5 style="color: red">${requestScope.message} <fmt:message key="page.registration.error.message"/></h5></span>
</c:if>
</body>
</html>