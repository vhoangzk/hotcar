/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Lab06
 */
@Entity
@Table(name = "drivers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer userId;
    
    private Integer isOnline;
    private Integer rate;
    private Double rateCount;

    public Driver() {
    }

    public Driver(Integer userId, Integer isOnline, Integer rate, Double rateCount) {
        this.userId = userId;
        this.isOnline = isOnline;
        this.rate = rate;
        this.rateCount = rateCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Double getRateCount() {
        return rateCount;
    }

    public void setRateCount(Double rateCount) {
        this.rateCount = rateCount;
    }
    
}
