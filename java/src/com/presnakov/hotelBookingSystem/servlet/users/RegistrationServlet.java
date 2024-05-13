package com.presnakov.hotelBookingSystem.servlet.users;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {


    private final UserService userService = UserService.getInstance();
    private final Integer USER_ROLE_ID = 2;
    private final String IS_ACTIVE_USER = "true";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setAttribute("roles", Arrays.stream(UserRoleEnum.values()).toList());
        req.getRequestDispatcher(JspHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        CreateUserDto userDto = CreateUserDto.builder()
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .userRole(USER_ROLE_ID)
                .isActive(IS_ACTIVE_USER)
                .build();
        try {
            if (userService.getUserByEmail(userDto.getEmail()) == null) {
                userService.create(userDto);
                req.setAttribute("isCreate", true);
                resp.sendRedirect("/login");
            } else {
                req.setAttribute("isCreate", false);
                req.setAttribute("message", userDto.getEmail());
                req.getRequestDispatcher(JspHelper.getPath("registration"))
                        .include(req, resp);
            }
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}