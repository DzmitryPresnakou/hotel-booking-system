<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.save-user.title"/></title>
</head>
<body>
<div class="container py-5">
    <div class="row text-center mb-5">
        <div class="col-lg-7 mx-auto">
            <h1 class="display-4"><fmt:message key="page.save-user.title"/></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <form action="${pageContext.request.contextPath}save-user" method="post">
                <input type="hidden" name="isActive" id="isActiveId" value="${requestScope.user.isActive}">
                <input type="hidden" name="id" id="userId" value="${requestScope.user.id}">

                <label for="firstNameId"><fmt:message key="page.save-user.first.name"/>:
                    <input type="text" name="firstName" id="firstNameId" value="${requestScope.user.firstName}">
                </label><br>
                <label for="lastNameId"><fmt:message key="page.save-user.last.name"/>:
                    <input type="text" name="lastName" id="lastNameId" value="${requestScope.user.lastName}">
                </label><br>
                <label for="emailId"><fmt:message key="page.save-user.email"/>:
                    <input type="text" name="email" id="emailId" value="${requestScope.user.email}">
                </label><br>
                <label for="passwordId"><fmt:message key="page.save-user.password"/>:
                    <input type="password" name="password" id="passwordId" value="${requestScope.user.password}">
                </label><br>

                <label for="roleId"><fmt:message key="page.save-user.role"/>:
                    <select name="role" id="roleId">
                        <c:forEach var="role" items="${requestScope.roles}">
                            <option value="${role}">${role}</option>
                        </c:forEach>
                    </select>
                </label><br>
                <button type="submit"><fmt:message key="page.save-user.submit.button"/></button>

                <c:if test="${not empty requestScope.errors}">
                    <div style="color: red">
                        <c:forEach var="error" items="${requestScope.errors}">
                            <span><fmt:message key="${error.code}"/></span>
                        </c:forEach>
                    </div>
                </c:if>
            </form>
            <span><h5 style="color: red">${requestScope.message}</h5></span>
        </div>
    </div>
</div>
</body>
</html>