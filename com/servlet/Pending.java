package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
@MultipartConfig
public class Pending extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            JSONObject object1 = (JSONObject) new JSONParser().parse(req.getParameter("Pending"));
            new DbServlet().pendingRecord(object1);
            
        }
        catch(Exception e){
                System.out.println(e);
        }
    }

   
  
}
