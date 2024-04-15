package com.presnakov.hotelBookingSystem.servlet;

import com.presnakov.hotelBookingSystem.dao.RoomDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/first")
public class FirstServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (var writer = resp.getWriter()) {
            writer.write("<h1>Hello from First Servlet!</h1>");
            writer.write("<br><h2>All rooms</h2>");
            writer.write("<br><h2>" + RoomDao.getInstance().findAll() + "</h2>");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}