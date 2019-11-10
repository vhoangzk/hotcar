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
@Table(name = "drivers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer userId;

    private Integer isOnline;
    private Integer isBusy;
    private Double rate;
    private Integer rateCount;
    private String driverType;
    private String latitude;
    private String longitude;

    public Integer getIsBusy() {
        return isBusy;
    }

    public void setIsBusy(Integer isBusy) {
        this.isBusy = isBusy;
    }
    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Driver() {
    }

    public Driver(Integer userId, Integer isOnline, Integer isBusy, Double rate, Integer rateCount, String driverType) {
        this.userId = userId;
        this.isOnline = isOnline;
        this.isBusy = isBusy;
        this.rate = rate;
        this.rateCount = rateCount;
        this.driverType = driverType;
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

    public Double getRate() {
        return Constants.formatNumber(rate);
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getRateCount() {
        return rateCount;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }

}
