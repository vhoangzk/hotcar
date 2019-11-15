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
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
    public static Integer DISTANCE_TO_FIND = 2;
    
    public static final int TRIP_STATUS_APPROACHING = 1;
    public static final int TRIP_STATUS_INPROGRESS = 2;
    public static final int TRIP_STATUS_PENDING_PAYMENT = 3;
    public static final int TRIP_STATUS_FINISH = 4;
    public static final int TRIP_STATUS_ARRIVED_A = 6;
    public static final int TRIP_STATUS_ARRIVED_B = 7;
    public static final int TRIP_STATUS_START_TASK = 8;
    
    public static Integer DRIVER_BUSY = 0;
    public static Integer DRIVER_IDLE = 1;
    
    public static Integer DRIVER_ONLINE = 1;
    public static Integer DRIVER_OFFLINE = 0;
    
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
        String uploadRootPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "uploads";
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
    
    public static long getLongTimeStamp() {
        Date d = new Date();
        long t = d.getTime() / 1000;
        return t;
    }
    
    public static String getDateTime(long time) {
//        Timestamp ts = new Timestamp(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time * 1000L);
        return sdf.format(date);
    }
    
    public static Double estimateFare(Double distance) {
        if (distance < 1) {
            return (double) AMOUNT_PER_KILOMETER;
        } else {
            Double estimate = distance * AMOUNT_PER_KILOMETER;
            return formatNumber(estimate);
        }
        
    }
    
    public static Double formatNumber(Double number) {
        try {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
            DecimalFormat df = new DecimalFormat("#,###");
            df.setRoundingMode(RoundingMode.CEILING);
            String str = df.format(number);
            Double rtn = nf.parse(str).doubleValue();
            return rtn;
        } catch (ParseException ex) {
            Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
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
            Double rtn = formatNumber(dist);
            return (rtn);
        }
    }
    
    public static String generateString(Integer length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        
        return generatedString;
    }
    
    public static String getBaseEnvLinkURL() {
        
        String baseEnvLinkURL = null;
        HttpServletRequest currentRequest
                = ((ServletRequestAttributes) RequestContextHolder.
                        currentRequestAttributes()).getRequest();
        // lazy about determining protocol but can be done too
        baseEnvLinkURL = "http://" + currentRequest.getServerName();
//        System.out.println(currentRequest.getLocalPort());
//        if (currentRequest.getLocalPort() != 80) {
//            baseEnvLinkURL += ":" + currentRequest.getLocalPort();
//        }
//        if (!StringUtils.isEmpty(currentRequest.getContextPath())) {
//            baseEnvLinkURL += currentRequest.getContextPath();
//        }
        return baseEnvLinkURL;
    }
    
}
