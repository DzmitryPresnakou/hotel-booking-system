package com.presnakov.hotelBookingSystem.servlet.rooms;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.entity.RoomStatusEnum;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import com.presnakov.hotelBookingSystem.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rooms")
public class RoomsServlet extends HttpServlet {

    private final RoomService roomService = RoomService.getInstance();
    private final String CONTENT_TYPE = "text/html";
    private final RoomStatusEnum ROOM_STATUS_AVAILABLE = RoomStatusEnum.AVAILABLE;
    private final UserRoleEnum USER_ROLE_ADMIN = UserRoleEnum.ADMIN;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        var userDto = (UserCompleteDto) req.getSession().getAttribute("user");

        if (userDto.getUserRoleDto().getUserRoleEnum().equals(USER_ROLE_ADMIN)) {
            req.setAttribute("isAdmin", true);
        } else {
            req.setAttribute("isAdmin", false);
        }
        req.setAttribute("rooms", roomService.findAll());
        req.setAttribute("availableRoom", ROOM_STATUS_AVAILABLE);
        req.getRequestDispatcher(JspHelper.getPath("rooms"))
                .forward(req, resp);
    }
}