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
@Table(name = "trip")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer passengerId;
    private Integer driverId;
    private Integer vehicleType;
    private Integer startTime;
    private Integer endTime;
    private Integer startLat;
    private String startLong;
    private String startLocation;
    private String endLat;
    private String endLong;
    private String endLocation;
    private String distance;
    private Integer status;
    private Double estimateFare;
    private Double actualFare;
    private Double driverRate;
    private Double passengerRate;

    public Trip() {
    }

    public Trip(Integer id, Integer passengerId, Integer driverId, Integer vehicleType, Integer startTime, Integer endTime, Integer startLat, String startLong, String startLocation, String endLat, String endLong, String endLocation, String distance, Integer status, Double estimateFare, Double actualFare, Double driverRate, Double passengerRate) {
        this.id = id;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.vehicleType = vehicleType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLat = startLat;
        this.startLong = startLong;
        this.startLocation = startLocation;
        this.endLat = endLat;
        this.endLong = endLong;
        this.endLocation = endLocation;
        this.distance = distance;
        this.status = status;
        this.estimateFare = estimateFare;
        this.actualFare = actualFare;
        this.driverRate = driverRate;
        this.passengerRate = passengerRate;
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

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getStartLat() {
        return startLat;
    }

    public void setStartLat(Integer startLat) {
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getEstimateFare() {
        return estimateFare;
    }

    public void setEstimateFare(Double estimateFare) {
        this.estimateFare = estimateFare;
    }

    public Double getActualFare() {
        return actualFare;
    }

    public void setActualFare(Double actualFare) {
        this.actualFare = actualFare;
    }

    public Double getDriverRate() {
        return driverRate;
    }

    public void setDriverRate(Double driverRate) {
        this.driverRate = driverRate;
    }

    public Double getPassengerRate() {
        return passengerRate;
    }

    public void setPassengerRate(Double passengerRate) {
        this.passengerRate = passengerRate;
    }
    
    
   
}
