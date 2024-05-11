package com.presnakov.hotelBookingSystem.filter;

import com.presnakov.hotelBookingSystem.dto.user.UserCompleteDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.presnakov.hotelBookingSystem.datasourse.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN, REGISTRATION, ROOMS);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isPublicPath(uri) || isUserLoggedIn(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            reject(servletRequest, servletResponse);
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = (UserCompleteDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }

    private void reject(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        var prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
        ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage : LOGIN);
    }
}