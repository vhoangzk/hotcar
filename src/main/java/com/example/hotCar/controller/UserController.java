/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Lab06
 */
@Controller
@RequestMapping("/web")
public class UserController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "homepage";
    }
}
