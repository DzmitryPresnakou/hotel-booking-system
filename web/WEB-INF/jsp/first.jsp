<%@ page import="com.presnakov.hotelBookingSystem.service.RoomOrderService" %>
<%@ page import="com.presnakov.hotelBookingSystem.dto.room.RoomOrderDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>First</title>
</head>
<body>
<h1>Список отелей</h1>
<ul>
        <%
             Long roomId = Long.valueOf(request.getParameter("roomId"));
             List<RoomOrderDto> roomOrders = RoomOrderService.getInstance().findAllByRoomId(roomId);
             for (RoomOrderDto roomOrder : roomOrders) {
                 out.write(String.format("<li>%s</li>", roomOrder.getRoomId()));
             }
        %>
</body>
</html>