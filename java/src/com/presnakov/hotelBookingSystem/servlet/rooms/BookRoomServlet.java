package com.presnakov.hotelBookingSystem.servlet.rooms;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.order.CreateOrderDto;
import com.presnakov.hotelBookingSystem.dto.room.CreateRoomDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomCompleteDto;
import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.dto.user.UserDto;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.presnakov.hotelBookingSystem.datasourse.UrlPath.PLACE_ORDER;
import static com.presnakov.hotelBookingSystem.datasourse.UrlPath.ROOMS;

@WebServlet("/book-room")
public class BookRoomServlet extends HttpServlet {

    private final RoomService roomService = RoomService.getInstance();

    private final OrderStatusService orderStatusService = OrderStatusService.getInstance();
    private final PaymentStatusService paymentStatusService = PaymentStatusService.getInstance();
    private final RoomOrderService roomOrderService = RoomOrderService.getInstance();

    private final String CONTENT_TYPE = "text/html";
    private final String ORDER_STATUS_OPEN = "OPEN";
    private final String PAYMENT_STATUS_DECLINED = "DECLINED";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        Integer id = Integer.valueOf(req.getParameter("id"));
        RoomCompleteDto roomCompleteDto = roomService.getRoom(id);
        req.setAttribute("room", roomCompleteDto);
        req.getRequestDispatcher(JspHelper.getPath("book-room"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType(CONTENT_TYPE);
        try {
            UserCompleteDto userCompleteDto = (UserCompleteDto) req.getSession().getAttribute("user");
            Integer id = Integer.valueOf(req.getParameter("id"));

            RoomCompleteDto roomCompleteDto = roomService.getRoom(id);
            CreateOrderDto createOrderDto = CreateOrderDto.builder()
                    .id(id)
                    .user(userCompleteDto.getId())
                    .room(roomCompleteDto.getId())
                    .orderStatus(orderStatusService.findByStatus(ORDER_STATUS_OPEN).getId())
                    .paymentStatus(paymentStatusService.findByStatus(PAYMENT_STATUS_DECLINED).getId())
                    .checkInDate(req.getParameter("checkInDate"))
                    .checkOutDate(req.getParameter("checkOutDate"))
                    .build();
            roomOrderService.create(createOrderDto);

//        CreateRoomDto createRoomDto = CreateRoomDto.builder()
//                .occupancy(roomCompleteDto.getOccupancy())
//                .roomClassDto(roomCompleteDto.getRoomClassDto().getId())
//                .roomStatusDto(roomCompleteDto.getRoomStatusDto().getId()) //??
//                .hotelDto(roomCompleteDto.getHotelDto().getId())
//                .build();
//        roomService.update(createRoomDto);

            resp.sendRedirect(PLACE_ORDER);
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}