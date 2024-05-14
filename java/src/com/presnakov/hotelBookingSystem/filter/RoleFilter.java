package com.presnakov.hotelBookingSystem.filter;

import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.presnakov.hotelBookingSystem.datasourse.UrlPath.*;

@WebFilter(urlPatterns = {"/users/*", "/orders", "/save-user"})
public class RoleFilter implements Filter {

    private static final Set<String> NONPUBLIC_PATH = Set.of(USERS, ORDERS, SAVE_USER, DELETE_USER);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isNonpublicPath(uri) && isAdmin(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            reject(servletRequest, servletResponse);
        }
    }

    private boolean isAdmin(ServletRequest servletRequest) {
        var user = (UserCompleteDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        if (isUserLoggedIn(servletRequest)) {
            return UserRoleEnum.ADMIN.equals(user.getUserRoleDto().getUserRoleEnum());
        }
        return false;
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = (UserCompleteDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }

    private boolean isNonpublicPath(String uri) {
        return NONPUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }

    private void reject(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        var prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
        ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage : LOGIN);
    }
}