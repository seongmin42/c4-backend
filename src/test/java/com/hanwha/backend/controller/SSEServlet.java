package com.hanwha.backend.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SSEServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        // Send initial SSE data
        response.getWriter().write("data: Initial data\n\n");
        response.getWriter().flush();

        // Simulate continuous SSE data updates
        while (true) {
            try {
                // Wait for a certain period (e.g., 1 second)
                Thread.sleep(1000);

                // Generate new SSE data
                String newData = "data: New data\n\n";

                // Send the new SSE data
                response.getWriter().write(newData);
                response.getWriter().flush();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
