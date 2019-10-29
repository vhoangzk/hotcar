/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.impl.CustomImpl;
import com.example.hotCar.model.LoginToken;
import com.example.hotCar.model.Users;
import com.example.hotCar.service.CustomService;
import com.example.hotCar.service.LoginTokenService;
import com.example.hotCar.service.UserService;
import com.example.hotCar.until.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
@RequestMapping("/api/user")
public class UserApiController {

    HttpStatus returnStt = HttpStatus.OK;
    public static Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    UserService userService;
    CustomService cusService = new CustomImpl();
    LoginTokenService tokenService;
    

    // -------------------Retrieve Single User------------------------------------------
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<Users> login(String email, String password, String gcm_id) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        ResponseEntity rtn;
        if (email.length() == 0 || password.length() == 0) {
            return Constants.JsonResponse(Constants.ERROR, null, "Missing param", null);
        }
        Users user = userService.findByEmail(email);
        if (user != null && Constants.encryptMD5(password).equals(user.getPassword())) {
            cusService.insertToken(user.getId(), gcm_id);
            rtn = Constants.JsonResponse(Constants.SUCCESS, user, "OK", null);
        } else {
            rtn = Constants.JsonResponse(Constants.ERROR, null, "Sai tên đăng nhập hoặc mật khẩu", null);
        }
        return rtn;
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
            Date d = new Date();
            long time = d.getTime() / 1000;
            String filename = Constants.uploadFile(image);

            u.setEmail(email);
            u.setPassword(Constants.encryptMD5(password));
            u.setFullName(name);
            u.setDateCreated((int) time);
            u.setImage(filename);
            u.setGender(gender);
            u.setPhone(phone);
            u.setStatus(Constants.STT_ACTIVE);
            LoginToken log = cusService.insertToken(u.getId(), gcm_id);
            tokenService.save(log);
            userService.save(u);
            return Constants.JsonResponse(Constants.SUCCESS, u, "OK", null);

        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public Users findContact(String name, String id) {
        if (id.equals("")) {
            ResponseEntity.notFound().build();
            return null;
        } else {
            Users contact = userService.getOne(Integer.valueOf(id));
            if (contact == null) {
                ResponseEntity.notFound().build();
            }
            return contact;
        }

    }

    @RequestMapping(value = "/contact/", method = RequestMethod.POST)
    public Users saveContact(@Valid @RequestBody Users contact) {
        return userService.save(contact);
    }

    @RequestMapping(value = "/contact/", method = RequestMethod.PUT)
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

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Users> deleteContact(@PathVariable(value = "id") Integer id) {
        Users contact = userService.getOne(id);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }

        userService.delete(contact);
        return ResponseEntity.ok().build();
    }
}
