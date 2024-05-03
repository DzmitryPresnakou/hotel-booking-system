<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page contentType="application/x-www-form-urlencoded;charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit user</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/users/save_user" method="post">

    <input type="hidden" name="isActive" id="isActiveId" value="${requestScope.user.isActive}">

    <label for="firstNameId">Id:
        <input type="text" name="id" id="userId" value="${requestScope.user.id}">
    </label><br>

    <label for="firstNameId">First name:
        <input type="text" name="firstName" id="firstNameId" value="${requestScope.user.firstName}">
    </label><br>
    <label for="lastNameId">Last name:
        <input type="text" name="lastName" id="lastNameId" value="${requestScope.user.lastName}">
    </label><br>
    <label for="emailId">Email:
        <input type="text" name="email" id="emailId" value="${requestScope.user.email}">
    </label><br>
    <c:if test="${requestScope.user != null}">
        <label for="passwordId">Password:
            <input type="password" name="password" id="passwordId" value="${requestScope.user.password}">
        </label><br>
        <label for="roleId">Role:
            <select name="role" id="roleId">
                <c:forEach var="role" items="${requestScope.roles}">
                    <option value="${role}">${role}</option>
                </c:forEach>
            </select>
        </label><br>

    </c:if>
    <button type="submit">Send</button>

    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>