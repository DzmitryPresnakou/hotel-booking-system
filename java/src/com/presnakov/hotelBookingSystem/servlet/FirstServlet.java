package com.presnakov.hotelBookingSystem.servlet;

import com.presnakov.hotelBookingSystem.dao.Dao;
import com.presnakov.hotelBookingSystem.dao.RoomDao;
import com.presnakov.hotelBookingSystem.entity.Room;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;

@WebServlet("/first")
public class FirstServlet extends HttpServlet {

    private final Dao<Long, Room> dao = RoomDao.getInstance();
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        try (var writer = response.getWriter()) {
            writer.write("<h1>Hello from First Servlet!</h1>");
            writer.write("<br><h2>All rooms</h2>");
            writer.write("<br><h2>" + dao.findAll() + "</h2>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (BufferedReader reader = request.getReader();
             Stream<String> lines = reader.lines()) {
            lines.forEach(System.out::println);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}