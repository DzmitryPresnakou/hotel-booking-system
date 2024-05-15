package com.presnakov.hotelBookingSystem.servlet.rooms;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.room.RoomCompleteDto;
import com.presnakov.hotelBookingSystem.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rooms/delete")
public class DeleteRoomServlet extends HttpServlet {

    private final RoomService roomService = RoomService.getInstance();
    private final String CONTENT_TYPE = "text/html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        Integer id = Integer.valueOf(req.getParameter("id"));
        RoomCompleteDto deletedRoom = roomService.getRoom(id);
        req.setAttribute("isDeleted", roomService.deleteRoom(id));
        req.setAttribute("deletedRoom", deletedRoom);
        req.getRequestDispatcher(JspHelper.getPath("rooms"))
                .include(req, resp);
    }
}