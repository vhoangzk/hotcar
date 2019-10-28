/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.service;

import com.example.hotCar.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lab06
 */
@Repository
public interface UserService extends JpaRepository<Users, Integer>{

    
    public Users findByEmail(String email);
    
    public Users findByPhone(String phone);
    
    public Users findByFullName(String name);
    
}
