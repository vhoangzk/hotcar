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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Autowired
    TripService tripService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "homepage";
    }

    @RequestMapping(value = "/chart", method = RequestMethod.GET)
    public String chart() {
        return "charts";
    }

    @RequestMapping(value = "/trip_tables", method = RequestMethod.GET)
    public String trip_tables(Model model) {

        List<Trip> t = tripService.findAll(new Sort(Sort.Direction.DESC, "id"));
        t.forEach((n) -> {
            Users d = userService.findById(n.getDriverId()).get();
            Users p = userService.findById(n.getPassengerId()).get();
            String time = Constants.getDateTime(n.getStartTime());
            n.setStartLat(d.getFullName());
            n.setEndLat(p.getFullName());
            n.setDistance(time);
        });

        model.addAttribute("trips", t);
        return "trip_tables";
    }
}
