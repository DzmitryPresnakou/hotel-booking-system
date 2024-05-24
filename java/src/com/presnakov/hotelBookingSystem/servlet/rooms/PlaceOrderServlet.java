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
    private final Integer CURRENCY_RATE_USD = 1;
    private final Integer CURRENCY_RATE_RUR = 100;

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

        if ("ru_RU".equals(req.getSession().getAttribute("lang"))) {
            req.setAttribute("currencyRate", CURRENCY_RATE_RUR);
        } else {
            req.setAttribute("currencyRate", CURRENCY_RATE_USD);
        }

        req.getRequestDispatcher(JspHelper.getPath("place-order"))
                .forward(req, resp);
    }
}