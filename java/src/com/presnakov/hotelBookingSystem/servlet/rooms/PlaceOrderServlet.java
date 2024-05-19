package com.presnakov.hotelBookingSystem.servlet.rooms;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.order.RoomOrderCompleteDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomCompleteDto;
import com.presnakov.hotelBookingSystem.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    private final RoomOrderService roomOrderService = RoomOrderService.getInstance();

    private final String CONTENT_TYPE = "text/html";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);

        Integer id = Integer.valueOf(req.getParameter("id"));

        RoomOrderCompleteDto roomOrderCompleteDto = roomOrderService.findByRoomId(id);
        RoomCompleteDto roomCompleteDto = roomOrderCompleteDto.getRoomCompleteDto();

        LocalDate checkInDate = roomOrderCompleteDto.getCheckInDate();
        LocalDate checkOutDate = roomOrderCompleteDto.getCheckOutDate();
        long period = DAYS.between(checkInDate, checkOutDate);
        req.setAttribute("period", period);
        req.setAttribute("room", roomCompleteDto);
        req.setAttribute("order", roomOrderCompleteDto);
        req.getRequestDispatcher(JspHelper.getPath("place-order"))
                .forward(req, resp);
    }
}