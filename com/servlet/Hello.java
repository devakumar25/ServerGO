package com.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// @WebServlet("/Hello")
public class Hello extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String a = req.getParameter("name");
        String mail = req.getParameter("mail");

        String c;
        if (mail.equals(DbServlet.mailChecking(mail))) {
            String errorMessage = "Mail Already Exists";
            String jsCode = "<script>alert('" + signin.escapeJsonString(errorMessage) + "'); window.location.href='landingPage.html';</script>";
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsCode);
        } else {
            try {

                c = encryptPassword(req.getParameter("password"));
                DbServlet.save(a, mail, c);
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int id = DbServlet.extractingID(mail);
            String name = DbServlet.extractingName(mail);
            String userDetails = "{\"userId\": " + id + ", \"name\": \"" + signin.escapeJsonString(name)
                    + "\", \"email\": \""
                    + signin.escapeJsonString(mail) + "\"}";

            // Encode user details as URL-safe string
            String encodedUserDetails = URLEncoder.encode(userDetails, StandardCharsets.UTF_8.toString());

            Cookie userCookie = new Cookie("user", encodedUserDetails);
            userCookie.setMaxAge(30 * 60); // Set cookie expiration time (in seconds)
            resp.addCookie(userCookie);

            resp.sendRedirect("testing.html");
        }

    }

    public static String encryptPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); // Get an instance of the SHA-256 algorithm

        byte[] hash = messageDigest.digest(password.getBytes()); // Hash the password using SHA-256

        StringBuilder hexString = new StringBuilder(); // Convert the hash to a hexadecimal string

        for (byte b : hash) {

            String hex = Integer.toHexString(0xff & b);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

}
