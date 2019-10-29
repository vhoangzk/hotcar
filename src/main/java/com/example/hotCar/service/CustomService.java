/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.service;

import com.example.hotCar.model.LoginToken;

/**
 *
 * @author Admin
 */
public interface CustomService {
    public LoginToken insertToken(Integer userId, String gcm_id);
}
