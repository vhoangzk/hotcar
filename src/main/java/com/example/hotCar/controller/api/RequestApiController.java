/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.model.Driver;
import com.example.hotCar.model.LoginToken;
import com.example.hotCar.model.Request;
import com.example.hotCar.model.Trip;
import com.example.hotCar.model.Users;
import com.example.hotCar.model.Vehicle;
import com.example.hotCar.service.DriverService;
import com.example.hotCar.service.LoginTokenService;
import com.example.hotCar.service.RequestService;
import com.example.hotCar.service.TripService;
import com.example.hotCar.service.UserService;
import com.example.hotCar.service.VehicleService;
import com.example.hotCar.until.Constants;
import com.example.hotCar.until.FCMNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class RequestApiController {

    @Autowired
    LoginTokenService lSer;
    @Autowired
    TripService tSer;
    @Autowired
    DriverService dSer;
    @Autowired
    UserService uSer;
    @Autowired
    VehicleService vSer;
    @Autowired
    RequestService rSer;

    @RequestMapping(value = "/showMyRequest", method = RequestMethod.POST)
    public ResponseEntity showMyRequest(String token) throws JsonProcessingException, Exception {
        LoginToken l = lSer.findByToken(token);
        Driver d = dSer.findByuserId(l.getUser_id());
        if (d == null) {
            return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
        }
        Users u = uSer.findById(d.getUserId()).get();
        ArrayList<Request> arrR = rSer.findAllBydriverId(d.getUserId());
        System.out.println("Request: " + arrR.size());
        if (arrR.isEmpty()) {
            return Constants.JsonResponse(Constants.SUCCESS, new ArrayList<>(), "OK", null);
        }

        Map<String, Object> data = new HashMap<>();
        ArrayList<Map> arrData = new ArrayList<>();
        arrR.forEach((r) -> {
            Users p = uSer.findById(r.getPassengerId()).get();
            Map<String, Object> driver = new HashMap<>();
            Map<String, Object> passenger = new HashMap<>();
            Map<String, Object> product = new HashMap<>();

            data.put("id", r.getId());
            data.put("passengerId", r.getPassengerId());
            data.put("link", "Hot Bike");
            data.put("driverId", r.getDriverId());
            data.put("startTime", "");
            data.put("startLat", r.getStartLat());
            data.put("startLong", r.getStartLong());
            data.put("endLat", r.getEndLat());
            data.put("endLong", r.getEndLong());
            data.put("startLocation", r.getStartLocation());
            data.put("endLocation", r.getEndLocation());
            data.put("requestTime", "");
            data.put("distance", "");
            data.put("estimate_fare", r.getEstimateFare());
            data.put("passengerRate", p.getRate());
            data.put("categoryName", "Hot Bike");

            driver.put("driverName", d.getUserId());
            driver.put("fullName", u.getFullName());
            driver.put("image", u.getLinkImage());
            driver.put("email", u.getEmail());
            driver.put("description", "");
            driver.put("gender", null);
            driver.put("phone", u.getPhone());
            driver.put("dob", "");
            driver.put("address", "");
            driver.put("balance", "0");
            driver.put("isOnline", d.getIsOnline());
            driver.put("rate", d.getRate());
            driver.put("rateCount", d.getRateCount());
            driver.put("carPlate", "8888");
            driver.put("carImage", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");

            passenger.put("id", String.valueOf(p.getId()));
            passenger.put("fullName", p.getFullName());
            passenger.put("image", p.getLinkImage());
            passenger.put("email", p.getEmail());
            passenger.put("description", "");
            passenger.put("gender", null);
            passenger.put("phone", p.getPhone());
            passenger.put("dob", "0000-00-00");
            passenger.put("address", "");
            passenger.put("balance", "0");
            passenger.put("isOnline", "1");
            passenger.put("rate", String.valueOf(p.getRate()));
            passenger.put("rateCount", String.valueOf(p.getRateCount()));

            product.put("id", "1");
            product.put("shopName", "");
            product.put("shopImage", "");
            product.put("categoryId", "");
            product.put("categoryName", "");
            product.put("phone", "");
            product.put("productName", "");
            product.put("productSize", "");
            product.put("quantity", "");
            product.put("price", "");
            product.put("shipFee", "");
            product.put("totalPrice", "");
            product.put("address", "");
            product.put("image", "");
            product.put("rate", "");
            product.put("rateCount", "");
            product.put("distance", "");

            data.put("driver", driver);
            data.put("passenger", passenger);
            data.put("product", product);
            arrData.add(data);
        });
        return Constants.JsonResponse(Constants.SUCCESS, arrData, "OK", null);
    }

    @RequestMapping(value = "/createRequest", method = RequestMethod.POST)
    public ResponseEntity createRequest(
            String token,
            Double startLat,
            Double endLat,
            String startLocation,
            String endLocation,
            Double startLong,
            Double endLong,
            Integer linkType
    ) throws JsonProcessingException {

        LoginToken l = lSer.findByToken(token);
        Users p = uSer.findById(l.getUser_id()).get();
        ArrayList<Driver> onlineD = dSer.findOnlineDriver(new Sort(Sort.Direction.DESC, "userId"));
        ArrayList<String> key = new ArrayList<>();;
        Double distance = Constants.distance(startLat, startLong, endLat, endLong, "K");
        Double estimateFare = Constants.estimateFare(distance);
        int[] count = new int[]{0};
        if (onlineD.size() > 0) {
            onlineD.forEach((d) -> {
                Double dDist = Constants.distance(Double.valueOf(d.getLatitude()), Double.valueOf(d.getLongitude()), startLat, startLong, "K");
                if (dDist < Constants.DISTANCE_TO_FIND && !Objects.equals(d.getUserId(), l.getUser_id())) {
                    Request r = new Request(
                            p.getId(),
                            d.getUserId(),
                            linkType,
                            String.valueOf(startLat),
                            String.valueOf(startLong),
                            startLocation,
                            String.valueOf(endLat),
                            String.valueOf(endLong),
                            endLocation,
                            distance,
                            String.valueOf(estimateFare)
                    );
                    rSer.save(r);
                    count[0]++;
                    LoginToken lp = lSer.findByUserId(d.getUserId());
                    key.add(lp.getToken());
                }
                System.out.println("Distance from driver to start: " + dDist);
                System.out.println("Number driver founded: " + count[0]);
            });
        }
        System.setProperty("file.encoding", "UTF-8");
        System.out.println("Start location: " + startLocation);
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("Key push size: " + key.size());
        if (key.size() > 0) {
            FCMNotification.push(new ArrayList(), "createRequest", "Bạn nhận được một yêu cầu chuyến đi", key);
        }
        Map<String, Object> map = new HashMap<>();
        ObjectMapper obj = new ObjectMapper();
        map.put("status", Constants.SUCCESS);
        map.put("data", "");
        map.put("estimate_fare", estimateFare);
        map.put("count", count[0]);
        map.put("message", "OK");
        return new ResponseEntity(obj.writeValueAsString(map), HttpStatus.OK);
    }

    @RequestMapping(value = "/cancelRequest", method = RequestMethod.POST)
    public ResponseEntity cancelRequest(String token) throws JsonProcessingException {
        LoginToken l = lSer.findByToken(token);
        Users p = uSer.findById(l.getUser_id()).get();

        ArrayList<Request> arrR = rSer.findAllBypassengerId(p.getId());
        ArrayList<String> key = new ArrayList<>();
        arrR.forEach((r) -> {
            LoginToken l2 = lSer.findByUserId(r.getDriverId());
            key.add(l2.getToken());
            rSer.delete(r);
        });
        
        FCMNotification.push(new ArrayList(), "cancelRequest", "Chuyen di da bi huy!", key);
        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }
}
