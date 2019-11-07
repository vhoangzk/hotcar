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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    LoginTokenService log;
    @Autowired
    TripService trip;
    @Autowired
    DriverService driverSer;
    @Autowired
    UserService user;
    @Autowired
    VehicleService vSer;
    @Autowired
    RequestService rSer;

    @RequestMapping(value = "/showMyRequest", method = RequestMethod.GET)
    public ResponseEntity showMyRequest(String token) throws JsonProcessingException, Exception {
        ArrayList<String> key = new ArrayList<>();
        key.add("efVciM4sTl4:APA91bHG9yTn2eMe3fgyOkcG-KqHc3BPNvWKhkuKH90lLNtfcLMNxgoMzXoFFpU-it01So55jYjKQlx6BzOjt2i-12mm9T1ltEARQO7xnHRnLjShs5_RA8J6cXhnahnBOQYUvwz74h9J");
        key.add("e3zDkKbzxCg:APA91bHrGxE8nv3b0GqJsMKGj9evDAnHAht8YtgBOG04TtQSbll7_iA1iyJvo1CZvK79ZiX-DsqWfBHM_9jJXLF4pqTdDnf_UCVproJ0liUQTHEgHksr1fPzMo7JhlYx3lCQP1eeBuz2");
        FCMNotification.push(new ArrayList(), 1, "Hello anh em", key);
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
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

        LoginToken l = log.findByToken(token);
        Users p = user.findById(l.getUser_id()).get();
        ArrayList<Driver> onlineD = driverSer.findByisOnlineAndDriverTypeAndIsBusy(1, linkType, Constants.DRIVER_IDLE);

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
                    System.out.println(dDist);
                    System.out.println(count[0]);
                }
                //push notification
            });
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
        LoginToken l = log.findByToken(token);
        Users p = user.findById(l.getUser_id()).get();

        rSer.deleteAllBypassengerId(p.getId());

        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }
}
