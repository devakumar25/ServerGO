package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminSignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter("mailadmin");
        String password = req.getParameter("passwordadmin");

        if (DbServlet.superSignIn(mail, password) == 1) {
            // Create a new cookie
            Cookie adminCookie = new Cookie("admin", "true");

            // Set the cookie's max age (in seconds)
            adminCookie.setMaxAge(60 * 60 * 24); // 1 day

            // Add the cookie to the response
            resp.addCookie(adminCookie);

            resp.sendRedirect("superadmin.html");
        } else {
            resp.sendRedirect("landingPage.html");
        }
    }
}
