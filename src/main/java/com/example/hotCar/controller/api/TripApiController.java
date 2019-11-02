/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.until.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class TripApiController {

    @RequestMapping(value = "/showTripDetail", method = RequestMethod.GET)
    public ResponseEntity showTripDetail(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/updateCoordinate", method = RequestMethod.GET)
    public ResponseEntity updateCoordinate(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/endTrip", method = RequestMethod.GET)
    public ResponseEntity endTrip(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/ratePassenger", method = RequestMethod.GET)
    public ResponseEntity ratePassenger(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/showMyTrip", method = RequestMethod.GET)
    public ResponseEntity showMyTrip(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public ResponseEntity changeStatus(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }
}
