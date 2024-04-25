package com.presnakov.hotelBookingSystem.servlet;

import com.presnakov.hotelBookingSystem.service.RoomOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/orders")
public class RoomOrderServlet extends HttpServlet {

    private final RoomOrderService roomOrderService = RoomOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        var roomId = Long.valueOf(req.getParameter("roomId"));

        try (var printWriter = resp.getWriter()) {
            printWriter.write("<h1>Заказанные номера</h1>");
            printWriter.write("<ul>");
            roomOrderService.findAllByRoomId(roomId).forEach(roomOrderDto -> printWriter.write("""
                    <li>
                    статус заказа - %s
                    </li>
                    """.formatted(roomOrderDto.getOrderStatusId())));
            printWriter.write("</ul>");
        }
    }
}