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
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public static Integer AMOUNT_PER_KILOMETER = 10000;
    public static Integer DISTANCE_TO_FIND = 1;

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
        Date d = new Date();
        long t = d.getTime() / 1000;
        String filename = FilenameUtils.getBaseName(image.getOriginalFilename()) + String.valueOf(t) + "." + FilenameUtils.getExtension(image.getOriginalFilename());
        byte[] bytes = image.getBytes();
        Path path = Paths.get(uploadRootPath + File.separator + filename);
        Files.write(path, bytes);

        return filename;
    }

    public static Integer getTimeStamp() {
        Date d = new Date();
        long t = d.getTime() / 1000;
        return (int) t;
    }

    public static Double estimateFare(Double distance) {
        if (distance < 1) {
            return (double) AMOUNT_PER_KILOMETER;
        } else {
            Double estimate = distance * AMOUNT_PER_KILOMETER;
            return estimate;
        }

    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if ("K".equals(unit)) {
                dist = dist * 1.609344;
            } else if ("N".equals(unit)) {
                dist = dist * 0.8684;
            }
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            Double rtn = Double.valueOf(df.format(dist));
            return (rtn);
        }
    }
}
