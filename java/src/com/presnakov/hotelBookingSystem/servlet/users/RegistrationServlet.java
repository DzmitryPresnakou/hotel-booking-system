package com.presnakov.hotelBookingSystem.servlet.users;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.exception.ValidationException;
import com.presnakov.hotelBookingSystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final String ADMIN_ROLE_ID = "1";
    private final String USER_ROLE_ID = "2";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", List.of("USER", "ADMIN"));
        req.getRequestDispatcher(JspHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CreateUserDto userDto = CreateUserDto.builder()
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .userRole(USER_ROLE_ID)
                .isActive(String.valueOf(true))
                .build();
        try {
            userService.create(userDto);
            resp.sendRedirect("/users");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}