/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.service;

import com.example.hotCar.model.Trip;
import java.util.ArrayList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Lab06
 */
public interface TripService extends JpaRepository<Trip, Integer> {

    ArrayList<Trip> findAllByPassengerId(Integer id);

    ArrayList<Trip> findAllByDriverId(Integer id);
    
//    @Query("SELECT t FROM Trip t WHERE t.passengerId = ?1 OR t.driverId = ?1 ORDER BY t.id DESC LIMIT 10 OFFSET ?2")
//    ArrayList<Trip> findMyTrip(Integer id, Integer offset);
    
    @Query("SELECT t FROM Trip t WHERE passengerId = ?1 OR driverId = ?1 ORDER BY t.id DESC")
    ArrayList<Trip> findMyTrip(Integer id);
}
