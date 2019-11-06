/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.model.Driver;
import com.example.hotCar.model.LoginToken;
import com.example.hotCar.model.Trip;
import com.example.hotCar.model.Users;
import com.example.hotCar.service.DriverService;
import com.example.hotCar.service.LoginTokenService;
import com.example.hotCar.service.TripService;
import com.example.hotCar.service.UserService;
import com.example.hotCar.until.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class TripApiController {

    @Autowired
    LoginTokenService log;
    @Autowired
    TripService trip;
    @Autowired
    DriverService driverSer;
    @Autowired
    UserService user;

    @RequestMapping(value = "/showTripDetail", method = RequestMethod.GET)
    public ResponseEntity showTripDetail(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/updateCoordinate", method = RequestMethod.GET)
    public ResponseEntity updateCoordinate(String token, String lat, @RequestParam("long") String lon) throws JsonProcessingException {
        Driver d = driverSer.findByuserId(log.findByToken(token).getUser_id());
        d.setLatitude(lat);
        d.setLongitude(lon);
        driverSer.save(d);
        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }

    @RequestMapping(value = "/endTrip", method = RequestMethod.GET)
    public ResponseEntity endTrip(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/ratePassenger", method = RequestMethod.GET)
    public ResponseEntity ratePassenger(Integer tripId, String token) throws JsonProcessingException {

        LoginToken l = log.findByToken(token);
        Trip t = trip.findById(tripId).get();
        Driver d = driverSer.findByuserId(t.getDriverId());
        Users u = user.findById(d.getUserId()).get();
        Users p = user.findById(t.getPassengerId()).get();

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> driver = new HashMap<>();
        Map<String, Object> passenger = new HashMap<>();
        Map<String, Object> product = new HashMap<>();

        data.put("id", t.getId());
        data.put("passengerId", t.getPassengerId());
        data.put("link", "");
        data.put("startTime", t.getStartTime());
        data.put("startLat", t.getStartLat());
        data.put("startLong", t.getStartLong());
        data.put("endLat", t.getEndLat());
        data.put("endLong", t.getEndLong());
        data.put("dateCreated", t.getEndTime());
        data.put("driverId", t.getDriverId());
        data.put("startLocation", t.getStartLocation());
        data.put("endLocation", t.getEndLocation());
        data.put("status", t.getStatus());
        data.put("endTime", t.getEndTime());
        data.put("distance", t.getDistance());
        data.put("estimateFare", t.getEstimateFare());
        data.put("actualFare", t.getActualFare());
        data.put("driverRate", t.getDriverRate());
        data.put("passengerRate", t.getPassengerRate());

        driver.put("driverName", d.getUserId());
        driver.put("identity", "");
        driver.put("rate", d.getRate());
        driver.put("rateCount", d.getRateCount());
        driver.put("imageDriver", u.getLinkImage());
        driver.put("carPlate", "8888");
        driver.put("carImage", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");
        driver.put("phone", u.getPhone());

        passenger.put("id", p.getId());
        passenger.put("passengerName", p.getFullName());
        passenger.put("rate", p.getRate());
        passenger.put("rateCount", p.getRateCount());
        passenger.put("imagePassenger", p.getLinkImage());
        passenger.put("phone", p.getPhone());

        product.put("id", "1");
        product.put("shopName", "");
        product.put("shopImage", "");
        product.put("categoryId", "");
        product.put("categoryName", "");
        product.put("marker", "");
        product.put("phone", "");
        product.put("productName", "");
        product.put("productSize", "");
        product.put("description", "");
        product.put("image", "");
        product.put("rate", "");
        product.put("rateCount", "");
        product.put("quantity", "");
        product.put("price", "");
        product.put("shipFee", "");
        product.put("rateShop", "");
        product.put("rateCountShop", "");

        data.put("driver", driver);
        data.put("passenger", passenger);
        data.put("product", product);

        Map<String, Object> map = new HashMap<>();
        ObjectMapper obj = new ObjectMapper();
        map.put("status", Constants.SUCCESS);
        map.put("data", data);
        map.put("totalTime", 1);
        map.put("totalPrice", 6761.8);
        map.put("pickUpAtA", "0");
        map.put("workAtB", "0");
        map.put("startTimeWorking", "0");
        map.put("endTimeWorking", "1572855533");
        map.put("message", "OK");
        return new ResponseEntity(obj.writeValueAsString(map), HttpStatus.OK);
    }

    public Map addComponent(Trip aTrip) {
        Trip t = trip.findById(aTrip.getId()).get();
        Driver d = driverSer.findByuserId(t.getDriverId());
        Users u = user.findById(d.getUserId()).get();
        Users p = user.findById(t.getPassengerId()).get();

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> driver = new HashMap<>();
        Map<String, Object> passenger = new HashMap<>();
        Map<String, Object> product = new HashMap<>();

        data.put("id", t.getId());
        data.put("passengerId", t.getPassengerId());
        data.put("link", "");
        data.put("startTime", t.getStartTime());
        data.put("startLat", t.getStartLat());
        data.put("startLong", t.getStartLong());
        data.put("endLat", t.getEndLat());
        data.put("endLong", t.getEndLong());
        data.put("dateCreated", t.getEndTime());
        data.put("driverId", t.getDriverId());
        data.put("startLocation", t.getStartLocation());
        data.put("endLocation", t.getEndLocation());
        data.put("status", t.getStatus());
        data.put("endTime", t.getEndTime());
        data.put("distance", t.getDistance());
        data.put("estimateFare", t.getEstimateFare());
        data.put("actualFare", t.getActualFare());
        data.put("driverRate", t.getDriverRate());
        data.put("passengerRate", t.getPassengerRate());

        driver.put("driverName", d.getUserId());
        driver.put("identity", "");
        driver.put("rate", d.getRate());
        driver.put("rateCount", d.getRateCount());
        driver.put("imageDriver", u.getLinkImage());
        driver.put("carPlate", "8888");
        driver.put("carImage", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");
        driver.put("phone", u.getPhone());

        passenger.put("id", p.getId());
        passenger.put("passengerName", p.getFullName());
        passenger.put("rate", p.getRate());
        passenger.put("rateCount", p.getRateCount());
        passenger.put("imagePassenger", p.getLinkImage());
        passenger.put("phone", p.getPhone());

        product.put("id", "1");
        product.put("shopName", "");
        product.put("shopImage", "");
        product.put("categoryId", "");
        product.put("categoryName", "");
        product.put("marker", "");
        product.put("phone", "");
        product.put("productName", "");
        product.put("productSize", "");
        product.put("description", "");
        product.put("image", "");
        product.put("rate", "");
        product.put("rateCount", "");
        product.put("quantity", "");
        product.put("price", "");
        product.put("shipFee", "");
        product.put("rateShop", "");
        product.put("rateCountShop", "");

        data.put("driver", driver);
        data.put("passenger", passenger);
        data.put("product", product);
        return data;
    }

    @RequestMapping(value = "/showMyTrip", method = RequestMethod.GET)
    public ResponseEntity showMyTrip(String token) throws JsonProcessingException {
        LoginToken l = log.findByToken(token);

        ArrayList<Trip> ts = trip.findAllByPassengerId(l.getUser_id());
        Map<String, Object> data = new HashMap<>();
//        ts.forEach((n) -> addComponent(n));
        for (int i = 0; i < ts.size(); i++) {
            Trip t = trip.findById(ts.get(i).getId()).get();
            Driver d = driverSer.findByuserId(t.getDriverId());
            Users u = user.findById(d.getUserId()).get();
            Users p = user.findById(ts.get(i).getPassengerId()).get();
           
            Map<String, Object> driver = new HashMap<>();
            Map<String, Object> passenger = new HashMap<>();
            Map<String, Object> product = new HashMap<>();

            data.put("id", t.getId());
            data.put("passengerId", t.getPassengerId());
            data.put("link", "");
            data.put("startTime", t.getStartTime());
            data.put("startLat", t.getStartLat());
            data.put("startLong", t.getStartLong());
            data.put("endLat", t.getEndLat());
            data.put("endLong", t.getEndLong());
            data.put("dateCreated", t.getEndTime());
            data.put("driverId", t.getDriverId());
            data.put("startLocation", t.getStartLocation());
            data.put("endLocation", t.getEndLocation());
            data.put("status", t.getStatus());
            data.put("endTime", t.getEndTime());
            data.put("distance", t.getDistance());
            data.put("estimateFare", t.getEstimateFare());
            data.put("actualFare", t.getActualFare());
            data.put("driverRate", t.getDriverRate());
            data.put("passengerRate", t.getPassengerRate());

            driver.put("driverName", d.getUserId());
            driver.put("identity", "");
            driver.put("rate", d.getRate());
            driver.put("rateCount", d.getRateCount());
            driver.put("imageDriver", u.getLinkImage());
            driver.put("carPlate", "8888");
            driver.put("carImage", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");
            driver.put("phone", u.getPhone());

            passenger.put("id", p.getId());
            passenger.put("passengerName", p.getFullName());
            passenger.put("rate", p.getRate());
            passenger.put("rateCount", p.getRateCount());
            passenger.put("imagePassenger", p.getLinkImage());
            passenger.put("phone", p.getPhone());

            product.put("id", "1");
            product.put("shopName", "");
            product.put("shopImage", "");
            product.put("categoryId", "");
            product.put("categoryName", "");
            product.put("marker", "");
            product.put("phone", "");
            product.put("productName", "");
            product.put("productSize", "");
            product.put("description", "");
            product.put("image", "");
            product.put("rate", "");
            product.put("rateCount", "");
            product.put("quantity", "");
            product.put("price", "");
            product.put("shipFee", "");
            product.put("rateShop", "");
            product.put("rateCountShop", "");

            data.put("driver", driver);
            data.put("passenger", passenger);
            data.put("product", product);
        }

        Map<String, Object> map = new HashMap<>();
        ObjectMapper obj = new ObjectMapper();
        map.put("status", Constants.SUCCESS);
        map.put("data", data);
        map.put("message", "OK");
        return new ResponseEntity(obj.writeValueAsString(map), HttpStatus.OK);
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public ResponseEntity changeStatus(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }
}
