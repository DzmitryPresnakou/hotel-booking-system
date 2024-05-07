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
    private final Integer USER_ROLE_ID = 2;
    private final String IS_ACTIVE_USER = "true";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null && !req.getParameter("id").isBlank()) {
            req.setAttribute("roles", Arrays.stream(UserRoleEnum.values()).toList());
            Integer id = Integer.valueOf(req.getParameter("id"));
            UserCompleteDto userCompleteDto = userService.getUser(id);
            req.setAttribute("user", userCompleteDto);
        }
        req.getRequestDispatcher(JspHelper.getPath("save-user"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer userRoleId;
        String isActive;
        String redirectPage;
        Integer id = 0;

        if (req.getParameter("id") != null && !req.getParameter("id").isBlank()) {
            id = Integer.valueOf(req.getParameter("id"));
            userRoleId = userRoleService.getUserRoleId(UserRoleEnum.valueOf((req.getParameter("role"))));
            isActive = req.getParameter("isActive");
            redirectPage = "/users";
        } else {
            userRoleId = USER_ROLE_ID;
            isActive = IS_ACTIVE_USER;
            redirectPage = "/login";
        }

        CreateUserDto userDto = CreateUserDto.builder()
                .id(id)
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .userRole(userRoleId)
                .isActive(isActive)
                .build();
        try {
            if (req.getParameter("id") != null && !req.getParameter("id").isBlank()) {
                userService.update(userDto);
                resp.sendRedirect(redirectPage);
            } else {
                if (userService.getUserByEmail(userDto.getEmail()) == null) {
                    userService.create(userDto);
                    req.setAttribute("isCreate", true);
                    resp.sendRedirect(redirectPage);
                } else {
                    req.setAttribute("isCreate", false);
                    req.setAttribute("message", "User with email " + userDto.getEmail() + " already exists");
                    req.getRequestDispatcher(JspHelper.getPath("save-user"))
                            .include(req, resp);
                }
            }

        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}