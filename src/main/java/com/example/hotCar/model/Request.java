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
@Table(name = "request")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer passengerId;
    private Integer driverId;
    private Integer vehicleType;
    private String startLat;
    private String startLong;
    private String startLocation;
    private String endLat;
    private String endLong;
    private String endLocation;
    private Double estimateDistance;
    private String estimateFare;

    public Request() {
    }

    public Request(Integer id, Integer passengerId, Integer driverId, Integer vehicleType, String startLat, String startLong, String startLocation, String endLat, String endLong, String endLocation, Double estimateDistance, String estimateFare) {
        this.id = id;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.vehicleType = vehicleType;
        this.startLat = startLat;
        this.startLong = startLong;
        this.startLocation = startLocation;
        this.endLat = endLat;
        this.endLong = endLong;
        this.endLocation = endLocation;
        this.estimateDistance = estimateDistance;
        this.estimateFare = estimateFare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLong() {
        return startLong;
    }

    public void setStartLong(String startLong) {
        this.startLong = startLong;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
        this.endLong = endLong;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Double getEstimateDistance() {
        return estimateDistance;
    }

    public void setEstimateDistance(Double estimateDistance) {
        this.estimateDistance = estimateDistance;
    }

    public String getEstimateFare() {
        return estimateFare;
    }

    public void setEstimateFare(String estimateFare) {
        this.estimateFare = estimateFare;
    }
    
    

   
}
