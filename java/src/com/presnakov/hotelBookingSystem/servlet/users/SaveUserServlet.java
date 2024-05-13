package com.presnakov.hotelBookingSystem.servlet.users;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.service.UserRoleService;
import com.presnakov.hotelBookingSystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/save-user")
public class SaveUserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final UserRoleService userRoleService = UserRoleService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setAttribute("roles", Arrays.stream(UserRoleEnum.values()).toList());
        Integer id = Integer.valueOf(req.getParameter("id"));
        UserCompleteDto userCompleteDto = userService.getUser(id);
        req.setAttribute("user", userCompleteDto);
        req.getRequestDispatcher(JspHelper.getPath("save-user"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        CreateUserDto userDto = CreateUserDto.builder()
                .id(Integer.valueOf(req.getParameter("id")))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .userRole(userRoleService.getUserRoleId(UserRoleEnum.valueOf((req.getParameter("role")))))
                .isActive(req.getParameter("isActive"))
                .build();
        try {
            userService.update(userDto);
            resp.sendRedirect("/users");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}