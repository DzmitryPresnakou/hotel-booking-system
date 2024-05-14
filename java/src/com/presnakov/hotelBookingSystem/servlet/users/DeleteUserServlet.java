package com.presnakov.hotelBookingSystem.servlet.users;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/delete")
public class DeleteUserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final String CONTENT_TYPE = "text/html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        Integer id = Integer.valueOf(req.getParameter("id"));
        String email = userService.getUser(id).getEmail();
        var userDto = (UserCompleteDto) req.getSession().getAttribute("user");

        if (!userDto.getId().equals(id)) {
            req.setAttribute("isDeleted", userService.deleteUser(id));
            req.setAttribute("message", email);
        } else {
            req.setAttribute("isTryToDeleteMyself", true);
        }
        req.getRequestDispatcher(JspHelper.getPath("users"))
                .include(req, resp);
    }
}