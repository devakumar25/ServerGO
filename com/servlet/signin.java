package com.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class signin extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter("mailin");
        String password = req.getParameter("passwordin");

        if (DbServlet.signInValidation(mail, password) == 1) {
            int id = DbServlet.extractingID(mail);
            String name = DbServlet.extractingName(mail);
            String userDetails = "{\"userId\": " + id + ", \"name\": \"" + escapeJsonString(name) + "\", \"email\": \""
                    + escapeJsonString(mail) + "\"}";

            // Encode user details as URL-safe string
            String encodedUserDetails = URLEncoder.encode(userDetails, StandardCharsets.UTF_8.toString());

            Cookie userCookie = new Cookie("user", encodedUserDetails);
            userCookie.setMaxAge(30 * 60); // Set cookie expiration time (in seconds)
            resp.addCookie(userCookie);

            resp.sendRedirect("testing.html");
        } 
        else {
            String errorMessage = "Invalid username or password";
            String jsCode = "<script>alert('" + escapeJsonString(errorMessage) + "'); window.location.href='landingPage.html';</script>";
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsCode);
        }
        
            
        
    }

    // Helper function to escape special characters in a JSON string
    public static String escapeJsonString(String value) {
        StringBuilder builder = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (c == '\"') {
                builder.append("\\\"");
            } else if (c == '\\') {
                builder.append("\\\\");
            } else if (c == '/') {
                builder.append("\\/");
            } else if (c == '\b') {
                builder.append("\\b");
            } else if (c == '\f') {
                builder.append("\\f");
            } else if (c == '\n') {
                builder.append("\\n");
            } else if (c == '\r') {
                builder.append("\\r");
            } else if (c == '\t') {
                builder.append("\\t");
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
