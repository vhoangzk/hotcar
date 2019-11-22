/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller;

import com.example.hotCar.model.Trip;
import com.example.hotCar.model.Users;
import com.example.hotCar.service.TripService;
import com.example.hotCar.service.UserService;
import com.example.hotCar.until.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Lab06
 */
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService userService;
    
    @Autowired
    TripService tripService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/activeUser", method = RequestMethod.POST)
    public ResponseEntity activeUser(Integer id) throws JsonProcessingException {

        try {
            Users u = userService.findById(id).get();
            if (Objects.equals(u.getStatus(), Constants.STT_ACTIVE)) {
                u.setStatus(Constants.STT_INACTIVE);
            } else {
                u.setStatus(Constants.STT_ACTIVE);
            }
            userService.save(u);
            return Constants.JsonResponse(Constants.SUCCESS, u.getStatus(), "Success", HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return Constants.JsonResponse(Constants.ERROR, "", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "/tripDelete", method = RequestMethod.POST)
    public ResponseEntity tripDelete(Integer id) throws JsonProcessingException {
        try {
            Trip t = tripService.findById(id).get();
            tripService.delete(t);
            return Constants.JsonResponse(Constants.SUCCESS, "", "Success", HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return Constants.JsonResponse(Constants.ERROR, "", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Users> u = userService.findAll();
        String baseUrl = Constants.getBaseEnvLinkURL() + "/uploads/";
        model.addAttribute("users", u);
        model.addAttribute("url", baseUrl);
        return "tables";
    }
}
