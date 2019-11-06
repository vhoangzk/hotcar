/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.File;
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
    private String image2;

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public Vehicle() {
    }

    public Vehicle(Integer userId, String carPlate, String model, Integer type, Integer status, String document, Integer dateCreated, String image, String image2) {
        this.userId = userId;
        this.carPlate = carPlate;
        this.model = model;
        this.type = type;
        this.status = status;
        this.document = document;
        this.dateCreated = dateCreated;
        this.image = image;
        this.image2 = image2;
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
    
    public String getLinkImage() {
        String link;
        link = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + this.image;
        File file = new File(link);
        if (!file.exists()) {
            return System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "defaultAvatar.jpg";
        } else {
            return link;
        }
    }
}
