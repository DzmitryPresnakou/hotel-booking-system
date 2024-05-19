package com.presnakov.hotelBookingSystem.servlet.rooms;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import com.presnakov.hotelBookingSystem.service.RoomOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orders")
public class RoomOrdersServlet extends HttpServlet {

    private final RoomOrderService roomOrderService = RoomOrderService.getInstance();
    private final String CONTENT_TYPE = "text/html";
    private final UserRoleEnum USER_ROLE_ADMIN = UserRoleEnum.ADMIN;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        var userDto = (UserCompleteDto) req.getSession().getAttribute("user");

        req.setAttribute("orders", roomOrderService.findAllByUserId(userDto.getId()));

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