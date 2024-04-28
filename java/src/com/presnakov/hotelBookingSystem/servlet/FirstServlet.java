package com.presnakov.hotelBookingSystem.servlet;

import com.presnakov.hotelBookingSystem.dao.Dao;
import com.presnakov.hotelBookingSystem.dao.HotelDao;
import com.presnakov.hotelBookingSystem.entity.Hotel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/first")
public class FirstServlet extends HttpServlet {

    private final Dao<Long, Hotel> dao = HotelDao.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        try (var writer = response.getWriter()) {

            writer.write("<h1>Список отелей</h1>");
            writer.write("<ul>");
            dao.findAll().forEach(hotel -> {
                writer.write("""
                        <li>
                            <a href="/orders?roomId=%d">%s</a>
                        </li>
                        """.formatted(hotel.getId(), hotel.getName()));
            });
            writer.write("</ul>");
        }
    }
}