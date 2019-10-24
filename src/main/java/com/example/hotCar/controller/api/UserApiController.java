/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.model.Users;
import com.example.hotCar.service.UserService;
import com.example.hotCar.until.CustomErrorType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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

/**
 *
 * @author Lab06
 */
@RestController
@RequestMapping("/api")
public class UserApiController {
    
    public static Logger logger = LoggerFactory.getLogger(UserApiController.class);
    
    @Autowired
    UserService userService;
    
    
    // -------------------Retrieve Single User------------------------------------------
 
    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public ResponseEntity<Users> login(@PathVariable("email") @RequestParam Integer email) throws JsonProcessingException {
        logger.info("Fetching User with id {}", email);
        Users user = userService.findByEmail(email);
        if (user == null) {
            logger.error("User with id {} not found.", email);
            return new ResponseEntity(new CustomErrorType("User with id " + email 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        ObjectMapper obj = new ObjectMapper();
        String json = obj.writeValueAsString(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ResponseEntity<List<Users>> listAll() {
        List<Users> listContact = userService.findAll();
        if (listContact.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listContact, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/contact/id", method = RequestMethod.GET)
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
