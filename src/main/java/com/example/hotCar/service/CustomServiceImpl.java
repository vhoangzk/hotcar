/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.service;

import com.example.hotCar.model.LoginToken;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Admin
 */
public class CustomServiceImpl implements CustomServiceCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    LoginTokenService tokenService;

    @Override
    public void insertToken(Integer userId, String gcm_id) {
        LoginToken checkLog = tokenService.findByUserId(userId);
        Date d = new Date();
        long time = d.getTime() / 1000;
        if (checkLog == null) {
            LoginToken token = new LoginToken(userId, gcm_id, (int) time);
            tokenService.save(token);
        } else {
            checkLog.setTime((int) time);
            checkLog.setToken(gcm_id);
            tokenService.save(checkLog);
        }
    }

}
