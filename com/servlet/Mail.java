package com.servlet;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;



import java.util.Properties;


public class Mail extends HttpServlet {

    // -------------------------------------------------CLIQ
    // BOT-------------------------------------------------------------------------------------

    // private static final String MESSAGE = "{\"text\":\"Critical Stock Levels Reached: Only 10 percent stock left. \",\"bot\":{\"name\":\"Stock-Alert\",\"image\":\"https://www.zoho.com/cliq/help/restapi/images/bot-custom.png\"},\"card\":{\"theme\":\"poll\"}}";
    // private static final String BOT_URL = "https://cliq.zoho.com/api/v2/bots/stockalert/message";
    // private static final String TOKEN_URL = "https://accounts.zoho.com/oauth/v2/token?client_id=1000.URDVXRE45E5VQYB0SEWDTQQYE302TL&grant_type=refresh_token&client_secret=8d0574d572ee72500f92f495fdc73e36abcb1623e8&refresh_token=1000.ef4c2657acedd46a6886d35a98b7aef6.5275b33a5b6884b9a5529681c0cde7dc";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ----------------------------------------------------------MAIL-----------------------------------------------------------------------

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("devakavi2523@gmail.com", "jjcgcpxcokkolmwm");
            }
        });
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("devakavi2523@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("devakumar.s@zohocorp.com"));
            message.setSubject("Critical Stock Levels Reached");
            message.setText(
                    "Subject: Urgent Request: Inventory Refill Needed\n\nDear Superadmin,\nI hope this email finds you well. I wanted to inform you that our current inventory levels have reached a critical point, with approximately 10 percent remaining. This shortage is starting to impact our distribution process, and we risk running out of stock completely.\nI kindly request your immediate attention to this matter. To avoid any disruptions in our operations and maintain customer satisfaction, I urge you to initiate the process of refilling our inventory as soon as possible. \n\nThank you for your attention to this matter.\n\nBest regards,\n\nServerGO Team");
            Transport.send(message);
            System.out.println("Email sent successfully.");
            response.getWriter().println(" working");

            // -----------------------------------------------------CLIQ   BOT---------------------------------------------------------
           

            // URL obj = null;
            // try {
            //     obj = new URL(TOKEN_URL);
            // } catch (MalformedURLException e) {
            //     throw new RuntimeException(e);
            // }

            // HttpURLConnection con = null;
            // try {
            //     con = (HttpURLConnection) obj.openConnection();
            //     con.setRequestMethod("POST");
            // } catch (IOException e) {
            //     throw new RuntimeException(e);
            // }

            // Scanner sc = null;
            // try {
            //     sc = new Scanner(con.getInputStream());
            // } catch (IOException e1) {
            //     e1.printStackTrace();
            // }
            // String token = (String) new JSONObject(sc.nextLine()).get("access_token");
            // URL url = new URL(BOT_URL);
            // HttpURLConnection http = (HttpURLConnection) url.openConnection();
            // http.setRequestMethod("POST");
            // http.setDoOutput(true);
            // http.setRequestProperty("Accept", "application/json");
            // http.setRequestProperty("Content-Type", "application/json");
            // http.setRequestProperty("Authorization", "Bearer " + token);
            // http.setDoOutput(true);
            // byte[] out = MESSAGE.getBytes(StandardCharsets.UTF_8);
            // OutputStream stream = http.getOutputStream();
            // stream.write(out);
            // System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            // http.disconnect();

        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error for send mail " + e.getMessage());
            response.getWriter().println("delete working");
        }
    }
}
