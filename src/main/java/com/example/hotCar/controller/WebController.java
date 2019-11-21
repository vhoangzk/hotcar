/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller;

import com.example.hotCar.model.Users;
import com.example.hotCar.service.UserService;
import com.example.hotCar.until.Constants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    UserService userService;
    
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "homepage";
    }
    
    @RequestMapping(value = "/chart", method = RequestMethod.GET)
    public String chart() {
        return "charts";
    }
    
    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tables(Model model) {
        List<Users> u = userService.findAll();
        String baseUrl = Constants.getBaseEnvLinkURL() + "/uploads/";
        model.addAttribute("users", u);
        model.addAttribute("url", baseUrl);
        return "tables";
    }
}
