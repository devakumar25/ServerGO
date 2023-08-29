package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class Chatbot extends HttpServlet {
    
        // try{
        // System.out.print(ask("which is the best server management platform"));
        // }
        // catch(Exception e){
        //     System.out.println("varataa");
        // }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
            String userInput = req.getParameter("userInput");
            String botResponse = ask(userInput);
            resp.setContentType("text/plain");
            PrintWriter out = resp.getWriter();
            out.write(botResponse);
            out.close();
            }
            catch(Exception e){
                System.out.print(e);
            }
        }

    public static String ask(String text) throws Exception {
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer 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");

        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", "You are a server expert chatbot in a server resource management product named ServerGO. Chat with customer in a friendly tone and reply if it is related to server management. if anyhting else is asked, say 'sorry i cant help you with that, please contact executive Devakumar number 8098992673' or 'say greetings and say thanks for visitng our servergo site' and if customer specify any company name specifically, please provide ServerGo company link. reply in 15 words maximum. and your name is considered to be Lyra here. here is the message from the customer:"+text);
        data.put("max_tokens", 2000);
        data.put("temperature", 1.0);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();

        return (new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text").toString());
    }
}