/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.model;

import com.example.hotCar.until.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Lab06
 */
@Entity
@Table(name = "login_token")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoginToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer userId;
    
    private String token;
    private Integer time;

    public LoginToken() {
    }

    public LoginToken(Integer userId, String token, Integer time) {
        this.userId = userId;
        this.token = token;
        this.time = time;
    }

    public LoginToken(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
        this.time = (int) Constants.getTimeStamp();
    }
    

    public Integer getUser_id() {
        return userId;
    }

    public void setUser_id(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
    
}
