package com.presnakov.hotelBookingSystem.servlet;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {

    private final RoomService roomService = RoomService.getInstance();
    private final String CONTENT_TYPE = "text/html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        req.setAttribute("rooms", roomService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("rooms"))
                .forward(req, resp);
    }
}