package com.presnakov.hotelBookingSystem.servlet.rooms;

import com.presnakov.hotelBookingSystem.dto.order.CreateOrderDto;
import com.presnakov.hotelBookingSystem.dto.order.RoomOrderCompleteDto;
import com.presnakov.hotelBookingSystem.dto.room.CreateRoomDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomCompleteDto;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.presnakov.hotelBookingSystem.datasourse.UrlPath.ORDERS;

@WebServlet("/place-order/cancel")
public class CancelOrderServlet extends HttpServlet {

    private final RoomService roomService = RoomService.getInstance();
    private final RoomOrderService roomOrderService = RoomOrderService.getInstance();
    private final OrderStatusService orderStatusService = OrderStatusService.getInstance();
    private final PaymentStatusService paymentStatusService = PaymentStatusService.getInstance();
    private final RoomStatusService roomStatusService = RoomStatusService.getInstance();

    private final String CONTENT_TYPE = "text/html";
    private final String ORDER_STATUS_REJECTED = "REJECTED";
    private final String PAYMENT_STATUS_DECLINED = "DECLINED";
    private final String ROOM_STATUS_AVAILABLE = "AVAILABLE";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        Integer roomId = Integer.valueOf(req.getParameter("roomId"));
        Integer orderId = Integer.valueOf(req.getParameter("orderId"));

        RoomCompleteDto roomCompleteDto = roomService.getRoom(roomId);
        RoomOrderCompleteDto roomOrderCompleteDto = roomOrderService.findById(orderId);

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .id(roomOrderCompleteDto.getId())
                .user(roomOrderCompleteDto.getUserDto().getId())
                .room(roomOrderCompleteDto.getRoomCompleteDto().getId())
                .orderStatus(orderStatusService.findByStatus(ORDER_STATUS_REJECTED).getId())
                .paymentStatus(paymentStatusService.findByStatus(PAYMENT_STATUS_DECLINED).getId())
                .checkInDate(String.valueOf(roomOrderCompleteDto.getCheckInDate()))
                .checkOutDate(String.valueOf(roomOrderCompleteDto.getCheckOutDate()))
                .build();
        CreateRoomDto roomDto = CreateRoomDto.builder()
                .id(roomCompleteDto.getId())
                .occupancy(roomCompleteDto.getOccupancy())
                .roomClassDto(roomCompleteDto.getRoomClassDto().getId())
                .roomStatusDto(roomStatusService.findByStatus(ROOM_STATUS_AVAILABLE).getId())
                .hotelDto(roomCompleteDto.getHotelDto().getId())
                .build();
        try {
            roomOrderService.update(createOrderDto);
            roomService.update(roomDto);
            req.setAttribute("isCancel", true);
            resp.sendRedirect(ORDERS);
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}