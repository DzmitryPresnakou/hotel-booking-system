package com.presnakov.hotelBookingSystem.servlet.rooms;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.room.CreateRoomDto;
import com.presnakov.hotelBookingSystem.dto.room.RoomCompleteDto;
import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.entity.RoomClassEnum;
import com.presnakov.hotelBookingSystem.entity.RoomStatusEnum;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.presnakov.hotelBookingSystem.datasourse.UrlPath.ROOMS;
import static com.presnakov.hotelBookingSystem.datasourse.UrlPath.USERS;

@WebServlet("/save-room")
public class SaveRoomServlet extends HttpServlet {

    private final RoomService roomService = RoomService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();
    private final RoomClassService roomClassService = RoomClassService.getInstance();
    private final RoomStatusService roomStatusService = RoomStatusService.getInstance();
    private final String CONTENT_TYPE = "text/html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        req.setAttribute("roomClasses", Arrays.stream(RoomClassEnum.values()).toList());
        req.setAttribute("roomStatuses", Arrays.stream(RoomStatusEnum.values()).toList());
        req.setAttribute("hotels", hotelService.findAll());

        Integer id = Integer.valueOf(req.getParameter("id"));
        RoomCompleteDto roomCompleteDto = roomService.getRoom(id);
        req.setAttribute("room", roomCompleteDto);
        req.getRequestDispatcher(JspHelper.getPath("save-room"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        CreateRoomDto roomDto = CreateRoomDto.builder()
                .id(Integer.valueOf(req.getParameter("id")))
                .occupancy(Integer.valueOf(req.getParameter("occupancy")))
                .roomClassDto(roomClassService.findByClass(req.getParameter("roomClass")).getId())
                .roomStatusDto(roomStatusService.findByStatus(req.getParameter("roomStatus")).getId())
                .hotelDto(hotelService.findByName(req.getParameter("hotel")).getId())
                .build();
        roomService.update(roomDto);
        resp.sendRedirect(ROOMS);
    }
}