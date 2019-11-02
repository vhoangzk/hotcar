/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.until.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    @RequestMapping(value = "/generalSettings", method = RequestMethod.GET)
    public ResponseEntity generalSetting(String token) throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        ArrayList arrListJob = new ArrayList();
        Map<String, Object> listJob = new HashMap<>();
        Map<String, Object> listJob2 = new HashMap<>();
        data.put("sign_up_start_point", "10000");
        data.put("admin_email", "");
        data.put("distance", "");
        data.put("driver_share_bonus", "");
        data.put("passenger_share_bonus", "");
        data.put("min_redeem_amount", "");
        data.put("min_transfer_amount", "");
        data.put("cancellation_fee", "");
        data.put("driver_earn", "");
        data.put("admin_phone_number", "");
        data.put("time_to_send_request_again", "");
        data.put("max_time_send_request", "");
        data.put("estimate_fare_speed", "");
        data.put("ppm_of_link_i", "");
        data.put("ppm_of_link_ii", "");
        data.put("ppm_of_link_iii", "");
        data.put("ppk_of_link_i", "");
        data.put("ppk_of_link_ii", "");
        data.put("ppk_of_link_iii", "");
        data.put("sf_of_link_i", "");
        data.put("sf_of_link_ii", "");
        data.put("sf_of_link_iii", "");

        listJob.put("link", "");
        listJob.put("name", "");
        listJob.put("startFare", "");
        listJob.put("feePerMinute", "");
        listJob.put("feePerKilometer", "");
        listJob.put("orderNumber", "");
        listJob.put("image", "");
        listJob.put("imgActive", "");
        listJob.put("imgMarker", "");
        listJob.put("imgSelected", "");
        listJob.put("pickUpAtA", "");
        listJob.put("workAtB", "");
        listJob.put("taskRate", "");
        listJob.put("taskDefaultTime", "");

        listJob2.put("link", "");
        listJob2.put("name", "");
        listJob2.put("startFare", "");
        listJob2.put("feePerMinute", "");
        listJob2.put("feePerKilometer", "");
        listJob2.put("orderNumber", "");
        listJob2.put("image", "");
        listJob2.put("imgActive", "");
        listJob2.put("imgMarker", "");
        listJob2.put("imgSelected", "");
        listJob2.put("pickUpAtA", "");
        listJob2.put("workAtB", "");
        listJob2.put("taskRate", "");
        listJob2.put("taskDefaultTime", "");

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
    public ResponseEntity showDistance(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/showListShopByCategory", method = RequestMethod.GET)
    public ResponseEntity showListShopByCategory(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }
}
