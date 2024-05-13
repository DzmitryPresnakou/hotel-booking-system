<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="header.jsp"%>
    <title><fmt:message key="page.login.title"/></title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email"><fmt:message key="page.login.email"/>:
        <input type="text" name="email" id="email" value="${param.email}" required>
    </label><br>
    <label for="password"><fmt:message key="page.login.password"/>:
        <input type="password" name="password" id="password" required>
    </label><br>
    <button type="submit"><fmt:message key="page.login.submit.button"/></button>
    <span><c:url value="/registration" var="inputURL"/>
            <a href="${inputURL}">
            <button type="button"><fmt:message key="page.login.register.button"/></button>
            </a>
    </span>

    <c:if test="${param.error != null}">
        <div style="color: red">
            <span><fmt:message key="page.login.error"/></span>
        </div>
    </c:if>
</form>
</body>
</html>