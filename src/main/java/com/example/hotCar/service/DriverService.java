/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.service;

import com.example.hotCar.model.Driver;
import java.util.ArrayList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lab06
 */
@Service
public interface DriverService extends JpaRepository<Driver, Integer>{
    public Driver findByuserId(Integer id);
    
    public ArrayList<Driver> findByisOnlineAndDriverType(Integer online, Integer type);
    
    @Query("SELECT d FROM Driver d WHERE d.isOnline = 1")
    public ArrayList<Driver> findOnlineDriver(Sort sort);
    
}
