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

public class BlockUsers extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            JSONObject obj = (JSONObject) new JSONParser().parse(req.getParameter("delRecord"));
            new DbServlet().blockRecord(obj);
            resp.getWriter().println("delete working");
        }
        catch(Exception e){
                System.out.println(e);
        }

    }
    
}
