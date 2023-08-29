package com.servlet;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.net.MalformedURLException;
import org.json.JSONObject;
import java.util.Scanner;

public class simpleMain {

    public static void  bill(int idid, String dor,String status, String mn,String name,String Comp,String Model, String Processor,String Memory, String hd,String add,int count){
        try{

        String billTemplate = "-------------------------------------------"  +
                              "                   INVOICE                 "+
                              "-------------------------------------------" 
                 +
                              "                    Order Details:                                      "  +
                              "                    Order ID:       "+idid+"                                   "+
                              "                    Date:           "+dor+"                  "  +
                              "                    Status:         "+status+"                                   " + 
                              "-------------------------------------------" + 
                
                              "                    Item Details:                                        " + 
                              
                              "                    Model No:       "+mn+"                                     " + 
                              "                    Name:           "+name+"                             " + 
                              "                    Company:        "+Comp+"                                 " + 
                              "                    Model:          "+Model+"                              " + 
                              "                    Processor:      "+Processor+"                                " + 
                              "                    Memory:         "+Memory+"                             " + 
                              "                    Hard Drive:     "+hd+"                        " + 
                              "                    Additional:     "+add+"                           " + 
                              "-------------------------------------------" + 
                
                              "                    Price Details:                                          " + 
                              "                    Model No:       9                                        " + 
                              
                              "                    Price:          "+count+"                                  "                           + 
                              "-------------------------------------------" + 
                
                              "                     Total Amount:   "+count+"                                 " + 
                              "-------------------------------------------";

        String MESSAGE = "{\"text\": \"" + billTemplate
                + "\",\"bot\":{\"name\":\"BillWhiz\"},\"card\":{\"title\":\"ServerGO-Receipt\",\"theme\":\"modern-inline\"}}";
        String BOT_URL = "https://cliq.zoho.com/company/64396901/api/v2/bots/stockalert/message";
        String TOKEN_URL = "https://accounts.zoho.com/oauth/v2/token?client_id=1000.URDVXRE45E5VQYB0SEWDTQQYE302TL&grant_type=refresh_token&client_secret=8d0574d572ee72500f92f495fdc73e36abcb1623e8&refresh_token=1000.ef4c2657acedd46a6886d35a98b7aef6.5275b33a5b6884b9a5529681c0cde7dc";

        URL obj = null;
        try {
            obj = new URL(TOKEN_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner sc = null;
        try {
            sc = new Scanner(con.getInputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String token = (String) new JSONObject(sc.nextLine()).get("access_token");
        URL url = new URL(BOT_URL);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("Authorization", "Bearer " + token);
        http.setDoOutput(true);
        byte[] out = MESSAGE.getBytes(StandardCharsets.UTF_8);
        OutputStream stream = http.getOutputStream();
        stream.write(out);
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }
    catch(Exception e){
        System.out.println(e);
    }

    }
}
