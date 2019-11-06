/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.service;

import com.example.hotCar.model.Trip;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lab06
 */
public interface TripService extends JpaRepository<Trip, Integer> {

    ArrayList<Trip> findAllByPassengerId(Integer id);

    ArrayList<Trip> findAllByDriverId(Integer id);
}
