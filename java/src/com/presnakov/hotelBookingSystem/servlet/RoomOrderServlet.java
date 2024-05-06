package com.presnakov.hotelBookingSystem.servlet;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.service.RoomOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orders")
public class RoomOrderServlet extends HttpServlet {

    private final RoomOrderService roomOrderService = RoomOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("orders", roomOrderService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("orders"))
                .forward(req, resp);
    }
}