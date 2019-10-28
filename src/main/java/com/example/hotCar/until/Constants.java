/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.until;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Lab06
 */
public class Constants {

    public static String SUCCESS = "SUCCESS";
    public static String ERROR = "ERROR";
    public static Integer STT_ACTIVE = 1;
    public static Integer STT_INACTIVE = 2;

    public static String encryptMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            // System.out.println("Số lượng ký tự: " + hashtext.length());
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResponseEntity JsonResponse(Object status, Object data, Object message, HttpStatus httpStatus) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        ObjectMapper obj = new ObjectMapper();
        map.put("status", status);
        map.put("data", data);
        map.put("message", message);
        if (httpStatus == null) {
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity(obj.writeValueAsString(map), httpStatus);
    }

}
