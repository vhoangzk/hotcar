/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.model.Driver;
import com.example.hotCar.model.LoginToken;
import com.example.hotCar.model.Users;
import com.example.hotCar.service.DriverService;
import com.example.hotCar.service.LoginTokenService;
import com.example.hotCar.service.UserService;
import com.example.hotCar.until.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Lab06
 */
@RestController
@RequestMapping("/api")
public class UserApiController {

    HttpStatus returnStt = HttpStatus.OK;
    public static Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    UserService userService;
    @Autowired
    LoginTokenService logService;
    @Autowired
    DriverService driverService;

    // -------------------Retrieve Single User------------------------------------------
    @RequestMapping(value = "/loginNomal", method = RequestMethod.GET)
    public ResponseEntity<Users> login(String email, String password, String gcm_id) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        if (email.length() == 0 || password.length() == 0) {
            return Constants.JsonResponse(Constants.ERROR, null, "Missing param", null);
        }
        Users u = userService.findByEmail(email);
        if (u != null && Constants.encryptMD5(password).equals(u.getPassword())) {
            //Save token
            LoginToken l;
            l = logService.findByUserId(u.getId());
            if (l == null) {
                l = new LoginToken(u.getId(), gcm_id);
            } else {
                l.setToken(gcm_id);
            }
            logService.save(l);
            //Get driver
            Driver d = driverService.findByuserId(u.getId());
            Map<String, Object> m = new HashMap<>();
            m.put("user_id", u.getId());
            m.put("points", "0");
            m.put("token", l.getToken());
            m.put("isDriver", (d != null) ? 1 : 0);
            m.put("typeTasker", 3);
            m.put("isActive", 0);
            m.put("driverActive", 0);
            m.put("typeAccount", 0);
            return Constants.JsonResponse(Constants.SUCCESS, m, "OK", null);
        } else {
            return Constants.JsonResponse(Constants.ERROR, null, "Sai tên đăng nhập hoặc mật khẩu", null);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Users> createNew(HttpServletRequest request, @RequestParam String name, Integer gender, String email, String phone, String password, MultipartFile image, String gcm_id) throws JsonProcessingException, IOException {
        if (email.length() == 0 || password.length() == 0) {
            return Constants.JsonResponse(Constants.ERROR, null, "Missing param", null);
        }
        if (image.isEmpty()) {
            return Constants.JsonResponse(Constants.ERROR, null, "Please select image", null);
        }
        Users checkExist = userService.findByEmail(email);
        if (checkExist != null) {
            return Constants.JsonResponse(Constants.ERROR, null, "Email đã tồn tại", null);
        } else {
            Users u = new Users();
            String filename = Constants.uploadFile(image);
            u.setEmail(email);
            u.setPassword(Constants.encryptMD5(password));
            u.setFullName(name);
            u.setDateCreated(Constants.getTimeStamp());
            u.setImage(filename);
            u.setGender(gender);
            u.setPhone(phone);
            u.setStatus(Constants.STT_ACTIVE);
            userService.save(u);
            LoginToken l = new LoginToken(u.getId(), gcm_id);
            logService.save(l);
            return Constants.JsonResponse(Constants.SUCCESS, u, "OK", null);

        }
    }

    @RequestMapping(value = "/showUserInfo", method = RequestMethod.GET)
    public ResponseEntity<Users> showUserInfo(String token) throws JsonProcessingException {
        if (token.equals("")) {
            return Constants.JsonResponse(Constants.ERROR, null, "Thiếu token", null);
        } else {
            LoginToken log = logService.findByToken(token);
            if (log == null) {
                return Constants.JsonResponse(Constants.ERROR, null, "Sai token", null);
            } else {
                Integer userId = log.getUser_id();
                Users u = userService.findById(userId).get();
                Map<String, Object> m = new HashMap<>();
                Map<String, Object> s = new HashMap<>();
                s.put("rate", "1");
                s.put("rateCount", "1");
                s.put("bankAccount", "1");
                s.put("status", "1");
                s.put("updatePending", "1");
                s.put("imageExtra", "1");
                s.put("imageExtra2", "1");
                s.put("isActive", "1");
                s.put("lat", "1");
                s.put("long", "1");
                s.put("driverRate", "1");
                s.put("driverCount", "1");
                s.put("isOnline", "1");
                s.put("linkType", "1");
                s.put("carPlate", "1");
                s.put("carType", "1");

                m.put("id", u.getId());
                m.put("fullName", u.getFullName());
                m.put("image", u.getFullName());
                m.put("email", u.getFullName());
                m.put("description", u.getFullName());
                m.put("isActive", u.getFullName());
                m.put("gender", u.getFullName());
                m.put("phone", u.getFullName());
                m.put("dob", u.getFullName());
                m.put("adddress", u.getFullName());
                m.put("balance", u.getFullName());
                m.put("isOnline", u.getFullName());
                m.put("passengerRate", u.getFullName());
                m.put("passengerRateCount", u.getFullName());
                m.put("stateId", u.getFullName());
                m.put("stateName", u.getFullName());
                m.put("cityId", u.getFullName());
                m.put("cityName", u.getFullName());
                m.put("typeOfAccount", u.getFullName());
                m.put("typeTasker", u.getFullName());
                m.put("account", u.getFullName());
                m.put("shop", s);
                m.put("car", "");
                return Constants.JsonResponse(Constants.SUCCESS, m, "OK", null);
            }
        }
    }

    @RequestMapping(value = "/online", method = RequestMethod.GET)
    public ResponseEntity online(String token, String status) throws JsonProcessingException {
        LoginToken log = logService.findByToken(token);
        Integer userId = log.getUser_id();
        Driver u = driverService.findByuserId(userId);
        u.setIsOnline(Integer.valueOf(status));
        driverService.save(u);
        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }

    @RequestMapping(value = "/updateProfile/", method = RequestMethod.GET)
    public ResponseEntity<Users> updateContact(@PathVariable(value = "id") Integer contactId,
            @Valid @RequestBody Users contactForm) {
        Users contact = userService.getOne(contactId);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }
        contact.setImage(contactForm.getImage());
        contact.setPhone(contactForm.getPhone());

        Users updatedContact = userService.save(contact);
        return ResponseEntity.ok(updatedContact);
    }

    @RequestMapping(value = "/updateShop", method = RequestMethod.GET)
    public ResponseEntity<Users> updateShop(@PathVariable(value = "id") Integer id) {
        Users contact = userService.getOne(id);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }

        userService.delete(contact);
        return ResponseEntity.ok().build();
    }
}
