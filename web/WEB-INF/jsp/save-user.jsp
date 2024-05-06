<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="header.jsp" %>
    <title>Save user</title>
</head>
<body>
<form action="${pageContext.request.contextPath}save-user" method="post">

    <input type="hidden" name="isActive" id="isActiveId" value="${requestScope.user.isActive}">
    <input type="hidden" name="id" id="userId" value="${requestScope.user.id}">

    <label for="firstNameId">First name:
        <input type="text" name="firstName" id="firstNameId" value="${requestScope.user.firstName}">
    </label><br>
    <label for="lastNameId">Last name:
        <input type="text" name="lastName" id="lastNameId" value="${requestScope.user.lastName}">
    </label><br>
    <label for="emailId">Email:
        <input type="text" name="email" id="emailId" value="${requestScope.user.email}">
    </label><br>

    <label for="passwordId">Password:
        <input type="password" name="password" id="passwordId" value="${requestScope.user.password}">
    </label><br>

    <c:if test="${requestScope.user != null}">

        <label for="roleId">Role:
            <select name="role" id="roleId">
                <c:forEach var="role" items="${requestScope.roles}">
                    <option value="${role}">${role}</option>
                </c:forEach>
            </select>
        </label><br>

    </c:if>
    <c:if test="${requestScope.user == null}">
        <input type="hidden" name="role" id="roleIdHidden" value="USER">

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
<c:if test="${requestScope.user != null}">
<div class="list-group-item">
    <c:url value="/users" var="inputURL"/>
    <h5>
        <a href="${inputURL}">Show users list</a>
    </h5>
</div>
</c:if>
</body>
</html>