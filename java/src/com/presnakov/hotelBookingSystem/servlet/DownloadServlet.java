package com.presnakov.hotelBookingSystem.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"filename.txt\"");
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (ServletOutputStream outputStream = response.getOutputStream();
             InputStream stream = DownloadServlet.class.getClassLoader().getResourceAsStream("application.properties")) {
            outputStream.write(stream.readAllBytes());
        }
    }
}