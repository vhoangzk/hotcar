/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.service;

import com.example.hotCar.model.LoginToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lab06
 */
@Repository
public interface LoginTokenService extends JpaRepository<LoginToken, Integer> {

    public LoginToken findByUserId(Integer userId);

    public LoginToken findByToken(String token);

}
