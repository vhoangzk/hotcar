/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.impl;

import com.example.hotCar.model.LoginToken;
import com.example.hotCar.service.CustomService;
import com.example.hotCar.service.LoginTokenService;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class CustomImpl implements CustomService {

    LoginTokenService tokenService;

    @Override
    public LoginToken insertToken(Integer userId, String gcm_id) {
        LoginToken checkLog = tokenService.findByUserId(userId);
        Date d = new Date();
        long time = d.getTime() / 1000;
        if (checkLog == null) {
            LoginToken token = new LoginToken(userId, gcm_id, (int) time);
            return token;
        } else {
            checkLog.setTime((int) time);
            checkLog.setToken(gcm_id);
            return checkLog;
        }
    }

}
