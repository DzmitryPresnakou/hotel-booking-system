<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%@include file="header.jsp" %>
    <div>
        <span>Content. Русский</span>>
        <p>Size: ${requestScope.rooms.size()}</p>
        <p>Id: ${requestScope.rooms.get(0).id}</p>
        <p>Id 2: ${requestScope.rooms[1].id}</p>
        <p>Map Id 2: ${sessionScope.roomsMap[1]}</p>
        <p>JSESSION id: ${cookie["JSESSIONID"]}, unique identifier</p>
        <p>Header: ${header["Cookie"]}</p>
        <p>Param id: ${param.id}</p>
        <p>Param test: ${param.test}</p>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>