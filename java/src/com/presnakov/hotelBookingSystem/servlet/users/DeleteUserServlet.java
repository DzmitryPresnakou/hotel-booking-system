package com.presnakov.hotelBookingSystem.servlet.users;

import com.presnakov.hotelBookingSystem.datasourse.JspHelper;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.valueOf(req.getParameter("id"));
        String email = userService.getUser(id).getEmail();

        if (userService.deleteUser(id)) {
            req.setAttribute("isDeleted", true);
            req.setAttribute("message", "User with email " +  email + " deleted");
        } else {
            req.setAttribute("isDeleted", false);
        }
        req.getRequestDispatcher(JspHelper.getPath("users"))
                .include(req, resp);
    }
}