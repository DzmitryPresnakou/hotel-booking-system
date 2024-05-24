<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.registration.title"/></title>
</head>
<body>
<div class="container py-5">
    <div class="row text-center mb-5">
        <div class="col-lg-7 mx-auto">
            <h1 class="display-4"><fmt:message key="page.registration.title"/></h1>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <form action="${pageContext.request.contextPath}/registration" method="post">
                <label for="firstNameId"><fmt:message key="page.registration.first.name"/>:
                    <input type="text" class="form-control" name="firstName" id="firstNameId">
                </label><br>
                <label for="lastNameId"><fmt:message key="page.registration.last.name"/>:
                    <input type="text" class="form-control" name="lastName" id="lastNameId">
                </label><br>
                <label for="emailId"><fmt:message key="page.registration.email"/>:
                    <input type="text" class="form-control" name="email" id="emailId">
                </label><br>
                <label for="passwordId"><fmt:message key="page.registration.password"/>:
                    <input type="password" class="form-control" name="password" id="passwordId">
                </label><br>
                <input type="hidden" name="role" id="roleIdHidden" value="USER">
                <button type="submit" class="btn btn-success"><fmt:message key="page.registration.submit.button"/></button>

                <c:if test="${not empty requestScope.errors}">
                    <div style="color: red">
                        <c:forEach var="error" items="${requestScope.errors}">
                            <span><fmt:message key="${error.code}"/></span>
                        </c:forEach>
                    </div>
                </c:if>
            </form>
            <c:if test="${not empty requestScope.message}">
            <span><h5 style="color: red">${requestScope.message} <fmt:message
                    key="page.registration.error.message"/></h5></span>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>