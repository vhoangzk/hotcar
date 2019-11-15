/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.model;

import com.example.hotCar.until.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Path;
import javax.persistence.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 *
 * @author Lab06
 */
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String image;
    private String email;
    private String password;
    private Integer gender;
    private String phone;
    private Integer status;
    private Integer dateCreated;
    private Double rate;
    private Integer rateCount;
    private Integer typeTasker;

    public Integer getTypeTasker() {
        return typeTasker;
    }

    public void setTypeTasker(Integer typeTasker) {
        this.typeTasker = typeTasker;
    }

    
    public Users() {
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String fullName, String image, String email, String password, Integer gender, String phone, Integer status, Integer dateCreated) {
        this.id = id;
        this.fullName = fullName;
        this.image = image;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Integer dateCreated) {
        this.dateCreated = dateCreated;
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

    public String getLinkImage() throws IOException {
        String path = System.getProperty("user.dir")  + "/src/main/resources/static/uploads/" + this.image;
        File file = new File(path);
        if (file.exists()) {
            return Constants.getBaseEnvLinkURL() + "/uploads/" + this.image;
        } else {
        return Constants.getBaseEnvLinkURL() + "/uploads/defaultAvatar.png";
        }
    }
}
