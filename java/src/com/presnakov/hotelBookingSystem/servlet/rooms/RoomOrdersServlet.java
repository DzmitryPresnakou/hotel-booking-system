package com.presnakov.hotelBookingSystem.servlet.rooms;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.datasourse.LocalDateFormatter;
import com.presnakov.hotelBookingSystem.dto.order.CreateOrderDto;
import com.presnakov.hotelBookingSystem.dto.order.RoomOrderCompleteDto;
import com.presnakov.hotelBookingSystem.dto.room.CreateRoomDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomCompleteDto;
import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.service.OrderStatusService;
import com.presnakov.hotelBookingSystem.service.RoomOrderService;
import com.presnakov.hotelBookingSystem.service.RoomService;
import com.presnakov.hotelBookingSystem.service.RoomStatusService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/orders")
public class RoomOrdersServlet extends HttpServlet {

    private final RoomOrderService roomOrderService = RoomOrderService.getInstance();

    private final RoomService roomService = RoomService.getInstance();
    private final OrderStatusService orderStatusService = OrderStatusService.getInstance();
    private final RoomStatusService roomStatusService = RoomStatusService.getInstance();

    private final String CONTENT_TYPE = "text/html";
    private final UserRoleEnum USER_ROLE_ADMIN = UserRoleEnum.ADMIN;

    private final String ORDER_STATUS_CLOSED = "CLOSED";
    private final String ROOM_STATUS_AVAILABLE = "AVAILABLE";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        var userDto = (UserCompleteDto) req.getSession().getAttribute("user");

        List<RoomOrderCompleteDto> roomOrderCompleteDtos = roomOrderService.findAll();

        for (RoomOrderCompleteDto roomOrderCompleteDto : roomOrderCompleteDtos) {

            if (LocalDateFormatter.format(String.valueOf(roomOrderCompleteDto.getCheckOutDate())).isBefore(LocalDate.now())) {
                RoomCompleteDto roomCompleteDto = roomService.getRoom(roomOrderCompleteDto.getRoomCompleteDto().getId());

                CreateOrderDto createOrderDto = CreateOrderDto.builder()
                        .id(roomOrderCompleteDto.getId())
                        .user(roomOrderCompleteDto.getUserDto().getId())
                        .room(roomOrderCompleteDto.getRoomCompleteDto().getId())
                        .orderStatus(orderStatusService.findByStatus(ORDER_STATUS_CLOSED).getId())
                        .paymentStatus(roomOrderCompleteDto.getPaymentStatusDto().getId())
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

                } catch (ValidationException exception) {
                    req.setAttribute("errors", exception.getErrors());
                    doGet(req, resp);
                }
            }
        }

        if (userDto.getUserRoleDto().getUserRoleEnum().equals(USER_ROLE_ADMIN)) {
            req.setAttribute("isAdmin", true);
            req.setAttribute("orders", roomOrderService.findAll());
        } else {
            req.setAttribute("isAdmin", false);
            req.setAttribute("orders", roomOrderService.findAllByUserId(userDto.getId()));
        }
        req.getRequestDispatcher(JspHelper.getPath("orders"))
                .forward(req, resp);
    }
}