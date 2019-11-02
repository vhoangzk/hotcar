/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.until;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    public static String uploadFile(MultipartFile image) throws IOException {
        String uploadRootPath = System.getProperty("user.dir") + File.separator + "uploads";
            File uploadRootDir = new File(uploadRootPath);
            if (!uploadRootDir.exists()) {
                uploadRootDir.mkdirs();
            }
            String filename = StringUtils.cleanPath(image.getOriginalFilename());
            byte[] bytes = image.getBytes();
            Path path = Paths.get(uploadRootPath + File.separator +  image.getOriginalFilename());
            Files.write(path, bytes);
            
            return filename;
    }
    
    public static Integer getTimeStamp(){
        Date d = new Date();
        long t = d.getTime() / 1000;
        return (int) t;
    }
}
