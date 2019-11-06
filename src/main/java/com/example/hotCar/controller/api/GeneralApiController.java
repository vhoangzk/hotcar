/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.model.Driver;
import com.example.hotCar.model.Trip;
import com.example.hotCar.model.Users;
import com.example.hotCar.service.DriverService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class GeneralApiController {

    @Autowired
    TripService tripService;

    @Autowired
    UserService userService;
    
    @Autowired
    DriverService driverService;

    @RequestMapping(value = "/generalSettings", method = RequestMethod.GET)
    public ResponseEntity generalSetting() throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        ArrayList arrListJob = new ArrayList();
        Map<String, Object> listJob = new HashMap<>();
        Map<String, Object> listJob2 = new HashMap<>();
        data.put("sign_up_start_point", "10000");
        data.put("admin_email", "vvhoangzk@gmail.com");
        data.put("distance", "6");
        data.put("driver_share_bonus", "0.01");
        data.put("passenger_share_bonus", "0.01");
        data.put("min_redeem_amount", "400");
        data.put("min_transfer_amount", "100");
        data.put("cancellation_fee", "0.01");
        data.put("driver_earn", "0.8");
        data.put("admin_phone_number", "0123456789");
        data.put("time_to_send_request_again", "30");
        data.put("max_time_send_request", "1");
        data.put("estimate_fare_speed", "30");
        data.put("ppm_of_link_i", "0");
        data.put("ppm_of_link_ii", "0");
        data.put("ppm_of_link_iii", "0");
        data.put("ppk_of_link_i", "0");
        data.put("ppk_of_link_ii", "0");
        data.put("ppk_of_link_iii", "0");
        data.put("sf_of_link_i", "0");
        data.put("sf_of_link_ii", "0");
        data.put("sf_of_link_iii", "0");

        listJob.put("link", "1");
        listJob.put("name", "Hoat Bike");
        listJob.put("startFare", "0.0");
        listJob.put("feePerMinute", "0.0");
        listJob.put("feePerKilometer", "0.0");
        listJob.put("orderNumber", "1");
        listJob.put("image", "http://bestapp.site/graduationproject/upload/job_type/1571658782.png");
        listJob.put("imgActive", "http://bestapp.site/graduationproject/upload/job_type/1571658782_active.png");
        listJob.put("imgMarker", "http://bestapp.site/graduationproject/upload/job_type/1571658782_active.png");
        listJob.put("imgSelected", "http://bestapp.site/graduationproject/upload/job_type/1571658782_selected.png");
        listJob.put("pickUpAtA", "1");
        listJob.put("workAtB", "0");
        listJob.put("taskRate", "0");
        listJob.put("taskDefaultTime", "0");

        listJob2.put("link", "6");
        listJob2.put("name", "Hot Car");
        listJob2.put("startFare", "0.0");
        listJob2.put("feePerMinute", "0.0");
        listJob2.put("feePerKilometer", "0.0");
        listJob2.put("orderNumber", "5");
        listJob2.put("image", "http://bestapp.site/graduationproject/upload/job_type/1571658814.png");
        listJob2.put("imgActive", "http://bestapp.site/graduationproject/upload/job_type/1571658886_active.png");
        listJob2.put("imgMarker", "http://bestapp.site/graduationproject/upload/job_type/1571658886_active.png");
        listJob2.put("imgSelected", "http://bestapp.site/graduationproject/upload/job_type/1571658814_selected.png");
        listJob2.put("pickUpAtA", "1");
        listJob2.put("workAtB", "0");
        listJob2.put("taskRate", "0");
        listJob2.put("taskDefaultTime", "0");

        arrListJob.add(listJob);
        arrListJob.add(listJob2);

        Map<String, Object> map = new HashMap<>();
        ObjectMapper obj = new ObjectMapper();
        map.put("status", Constants.SUCCESS);
        map.put("data", data);
        map.put("list_job", arrListJob);
        map.put("message", "OK");
        return new ResponseEntity(obj.writeValueAsString(map), HttpStatus.OK);
    }

    @RequestMapping(value = "/showDistance", method = RequestMethod.GET)
    public ResponseEntity showDistance(Integer tripId) throws JsonProcessingException {
        Trip t = tripService.findById(tripId).get();
        Users u = userService.findById(t.getPassengerId()).get();
        Driver d = driverService.findById(t.getDriverId()).get();

        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/showListShopByCategory", method = RequestMethod.GET)
    public ResponseEntity showListShopByCategory(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }
}
