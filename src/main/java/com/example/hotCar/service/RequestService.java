/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.service;

import com.example.hotCar.model.Request;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Lab06
 */
public interface RequestService extends JpaRepository<Request, Integer>{
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Request WHERE passengerId = ?1")
    void deleteAllBypassengerId(Integer id);
    
    ArrayList<Request> findAllBydriverId(Integer Id);
    
    ArrayList<Request> findAllBypassengerId(Integer Id);
}
