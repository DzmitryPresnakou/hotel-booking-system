package com.presnakov.hotelBookingSystem.servlet.rooms;

import com.presnakov.hotelBookingSystem.dto.order.RoomOrderCompleteDto;
import com.presnakov.hotelBookingSystem.entity.OrderStatusEnum;
import com.presnakov.hotelBookingSystem.service.RoomOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.presnakov.hotelBookingSystem.datasourse.UrlPath.ORDERS;

@WebServlet("/orders/delete")
public class DeleteOrderServlet extends HttpServlet {

    private final RoomOrderService roomOrderService = RoomOrderService.getInstance();
    private final String CONTENT_TYPE = "text/html";
    private final OrderStatusEnum ORDER_STATUS_CLOSED = OrderStatusEnum.CLOSED;
    private final OrderStatusEnum ORDER_STATUS_REJECTED = OrderStatusEnum.REJECTED;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        Integer id = Integer.valueOf(req.getParameter("id"));
        RoomOrderCompleteDto roomOrderCompleteDto = roomOrderService.findById(id);

        if (roomOrderCompleteDto.getOrderStatusDto().getOrderStatusEnum().compareTo(ORDER_STATUS_CLOSED) == 0
            || roomOrderCompleteDto.getOrderStatusDto().getOrderStatusEnum().compareTo(ORDER_STATUS_REJECTED) == 0) {
            req.setAttribute("isDeleted", roomOrderService.deleteRoomOrder(id));
        } else {
            req.setAttribute("isDeleted", false);

        }
        resp.sendRedirect(ORDERS);
    }
}