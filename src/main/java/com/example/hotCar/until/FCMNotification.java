/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.until;

import com.example.hotCar.controller.api.RequestApiController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class FCMNotification {

    // Method to send Notifications from server to client end.
    public final static String AUTH_KEY_FCM = "AAAAaAYV4R0:APA91bHejwPVCIIXjC5BfxelyeO37lL5bmZDtn3j6WHNJCgDr6WpguktkfS1Yy9QDMQrJN3pPtHxfuYSyE0p8ocQ4kYE_ZHZey84_8SHQH72jj7qH1rf6-SGTK0eX7z8o2YPRyuaFILE";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static void pushFCMNotification(String DeviceIdKey, JSONObject ob) throws Exception, IOException {

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        JSONObject data = new JSONObject();
        data.put("data", ob);
        data.put("to", DeviceIdKey.trim());
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        wr.write(data.toString());
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(ob.toString());
    }
    
    public static void push(ArrayList arr, String action, String message, ArrayList<String> key){
        JSONObject j = new JSONObject();
        j.put("data", arr);
        j.put("action", action);
        j.put("body", message);
        if (key.size() > 0) {
            key.forEach((n) -> {
//                System.out.println(n);
            try {
                FCMNotification.pushFCMNotification(n, j);
            } catch (Exception ex) {
                Logger.getLogger(RequestApiController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        }
    }
}
