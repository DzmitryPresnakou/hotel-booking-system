package com.presnakov.hotelBookingSystem.servlet;
import com.presnakov.hotelBookingSystem.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {

    private final RoomService roomService = RoomService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<h1>Список комнат</h1>");
            printWriter.write("<ul>");
            roomService.findAll().forEach(roomDto -> {
                printWriter.write("""
                        <li>
                            <a href="/orders?roomId=%d">%s</a>
                        </li>
                        """.formatted(roomDto.getId(), roomDto.getDescription()));
            });
            printWriter.write("</ul>");
        }
    }
}