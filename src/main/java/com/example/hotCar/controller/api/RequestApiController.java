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
public class RequestApiController {

    @RequestMapping(value = "/showMyRequest", method = RequestMethod.GET)
    public ResponseEntity showMyRequest(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/createRequest", method = RequestMethod.GET)
    public ResponseEntity createRequest(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }

    @RequestMapping(value = "/cancelRequest", method = RequestMethod.GET)
    public ResponseEntity cancelRequest(String token) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }
}
