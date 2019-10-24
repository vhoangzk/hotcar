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
@Table(name = "vehicle")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer userId;
    
    private String carPlate;
    private String model;
    private Integer type;
    private Integer status;
    private String document;
    private Integer dateCreated;
    private String image;

    public Vehicle() {
    }

    public Vehicle(Integer userId, String carPlate, String model, Integer type, Integer status, String document, Integer dateCreated, String image) {
        this.userId = userId;
        this.carPlate = carPlate;
        this.model = model;
        this.type = type;
        this.status = status;
        this.document = document;
        this.dateCreated = dateCreated;
        this.image = image;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Integer getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Integer dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
