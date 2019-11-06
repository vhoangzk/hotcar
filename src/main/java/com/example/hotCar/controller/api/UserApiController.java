/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.model.Driver;
import com.example.hotCar.model.LoginToken;
import com.example.hotCar.model.Users;
import com.example.hotCar.model.Vehicle;
import com.example.hotCar.service.DriverService;
import com.example.hotCar.service.LoginTokenService;
import com.example.hotCar.service.UserService;
import com.example.hotCar.service.VehicleService;
import com.example.hotCar.until.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    @Autowired
    VehicleService vehicleService;

    // -------------------Retrieve Single User------------------------------------------
    @RequestMapping(value = "/loginNomal", method = RequestMethod.GET)
    public ResponseEntity<Users> login(String email, String password, String gcm_id) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        if (email.length() == 0 || password.length() == 0) {
            return Constants.JsonResponse(Constants.ERROR, "", "Missing param", null);
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
            return Constants.JsonResponse(Constants.ERROR, "", "Sai tên đăng nhập hoặc mật khẩu", null);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<Users> createNew(@RequestParam String full_name,
            String email,
            String phone,
            String password,
            MultipartFile image
    ) throws JsonProcessingException, IOException {
        if (email.length() == 0 || password.length() == 0) {
            return Constants.JsonResponse(Constants.ERROR, "", "Missing param", null);
        }
        if (image.isEmpty()) {
            return Constants.JsonResponse(Constants.ERROR, "", "Please select image", null);
        }
        Users checkExist = userService.findByEmail(email);
        if (checkExist != null) {
            return Constants.JsonResponse(Constants.ERROR, "", "Email đã tồn tại", null);
        } else {
            Users u = new Users();
            String filename = Constants.uploadFile(image);
            u.setEmail(email);
            u.setPassword(Constants.encryptMD5(password));
            u.setFullName(full_name);
            u.setDateCreated(Constants.getTimeStamp());
            u.setImage(filename);
            u.setGender(1);
            u.setPhone(phone);
            u.setStatus(Constants.STT_ACTIVE);
            userService.save(u);
            return Constants.JsonResponse(Constants.SUCCESS, "", "Đăng ký thành công", null);

        }
    }

    @RequestMapping(value = "/showUserInfo", method = RequestMethod.GET)
    public ResponseEntity<Users> showUserInfo(String token) throws JsonProcessingException {
        if (token.equals("")) {
            return Constants.JsonResponse(Constants.ERROR, "", "Thiếu token", null);
        } else {
            LoginToken log = logService.findByToken(token);
            if (log == null) {
                return Constants.JsonResponse(Constants.ERROR, "", "Sai token", null);
            } else {
                Integer userId = log.getUser_id();
                Users u = userService.findById(userId).get();
                Driver d = driverService.findById(userId).get();
                Vehicle v = vehicleService.findByuserId(userId);
                Map<String, Object> m = new HashMap<>();
                Map<String, Object> s = new HashMap<>();
                Map<String, Object> c = new HashMap<>();
                s.put("rate", d.getRate());
                s.put("rateCount", d.getRateCount());
                s.put("bankAccount", "");
                s.put("status", "1");
                s.put("updatePending", "1");
                s.put("imageExtra", u.getLinkImage());
                s.put("imageExtra2", u.getLinkImage());
                s.put("isActive", "1");
                s.put("lat", "-28.9323405");
                s.put("long", "135.6806593");
                s.put("driverRate", "0");
                s.put("driverCount", "0");
                s.put("isOnline", "1");
                s.put("linkType", "0");
                s.put("carPlate", "8888");
                s.put("carType", "45");

                c.put("id", "2");
                c.put("vehiclePlate", "8888");
                c.put("vehicleType", "45");
                c.put("dateCreated", "2019");
                c.put("image1", "");
                c.put("image2", "");

                m.put("id", u.getId());
                m.put("fullName", u.getFullName());
                m.put("image", u.getLinkImage());
                m.put("email", u.getEmail());
                m.put("description", "");
                m.put("isActive", u.getStatus());
                m.put("gender", null);
                m.put("phone", u.getPhone());
                m.put("dob", "");
                m.put("adddress", "Ha Noi");
                m.put("balance", "10000");
                m.put("isOnline", u.getStatus());
                m.put("passengerRate", "10");
                m.put("passengerRateCount", "2");
                m.put("stateId", "0");
                m.put("stateName", "");
                m.put("cityId", "");
                m.put("cityName", "");
                m.put("typeAccount", "0");
                m.put("typeTasker", "1");
                m.put("account", "");
                m.put("shop", s);
                m.put("car", c);
                return Constants.JsonResponse(Constants.SUCCESS, m, "OK", null);
            }
        }
    }

    @RequestMapping(value = "/online", method = RequestMethod.POST)
    public ResponseEntity online(String token, String status) throws JsonProcessingException {
        LoginToken log = logService.findByToken(token);
        Integer userId = log.getUser_id();
        Driver u = driverService.findByuserId(userId);
        u.setIsOnline(Integer.valueOf(status));
        driverService.save(u);
        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }

    @RequestMapping(value = "/updateProfile/", method = RequestMethod.GET)
    public ResponseEntity<Users> updateProfile(
            Integer contactId
    ) {
        Users contact = userService.getOne(contactId);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }

        Users updatedContact = userService.save(contact);
        return ResponseEntity.ok(updatedContact);
    }

    @RequestMapping(value = "/registerShop", method = RequestMethod.POST)
    public ResponseEntity<Users> registerShop(
            String token,
            String fullName,
            String phone,
            Integer vehicleType,
            Integer driverType,
            String vehiclePlate,
            MultipartFile imageExtra,
            MultipartFile imageExtra2,
            MultipartFile avatar
    ) throws JsonProcessingException, IOException {
        if (token.length() == 0) {
            return Constants.JsonResponse(Constants.ERROR, "", "Thiếu param", null);
        }
        LoginToken l = logService.findByToken(token);
        if (l == null) {
            return Constants.JsonResponse(Constants.ERROR, "", "Sai token", null);
        }
        if (imageExtra.isEmpty()) {
            return Constants.JsonResponse(Constants.ERROR, "", "Thiếu ảnh 1", null);
        }
        if (imageExtra2.isEmpty()) {
            return Constants.JsonResponse(Constants.ERROR, "", "Thiếu ảnh 2", null);
        }

        Optional<Driver> check = driverService.findById(l.getUser_id());

        if (check.isPresent()) {
            return Constants.JsonResponse(Constants.ERROR, "", "Driver đã tồn tại!", null);
        }

        Users u = userService.findById(l.getUser_id()).get();
        String oldAvatar = u.getLinkImage();

        String image = Constants.uploadFile(imageExtra);
        String image2 = Constants.uploadFile(imageExtra2);
        Vehicle newV = new Vehicle(u.getId(), vehiclePlate, "2019", vehicleType, 1, "", Constants.getTimeStamp(), image, image2);
        vehicleService.save(newV);
        Driver newD = new Driver(u.getId(), 1, 0, 0, driverType);
        driverService.save(newD);
        
        Map<String, Object> map = new HashMap<>();
        ObjectMapper obj = new ObjectMapper();
        map.put("status", Constants.SUCCESS);
        map.put("data", "");
        map.put("is_active", 1);
        map.put("message", "OK");
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity(obj.writeValueAsString(map), httpStatus);
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

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public ResponseEntity<Users> forgotPassword(Integer id) {
        Users contact = userService.getOne(id);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }

        userService.delete(contact);
        return ResponseEntity.ok().build();
    }
}
