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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    @Autowired
    JavaMailSender mail;

    // -------------------Retrieve Single User------------------------------------------
    @RequestMapping(value = "/loginNormal", method = RequestMethod.GET)
    public ResponseEntity loginNormal(String email, String password, String gcm_id) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
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
            System.out.println("Login: " + u.getEmail());
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
            u.setRate(10.0);
            u.setRateCount(0);
            u.setTypeTasker(3);
            userService.save(u);
            System.out.println("Đăng ký OK");
            return Constants.JsonResponse(Constants.SUCCESS, "", "Đăng ký thành công", null);
        }
    }

    @RequestMapping(value = "/showUserInfo", method = RequestMethod.POST)
    public ResponseEntity<Users> showUserInfo(String token) throws JsonProcessingException, IOException {
        if (token.equals("")) {
            return Constants.JsonResponse(Constants.ERROR, "", "Thiếu token", null);
        } else {
            LoginToken log = logService.findByToken(token);
            if (log == null) {
                return Constants.JsonResponse(Constants.ERROR, "", "Sai token", null);
            } else {
                Integer userId = log.getUser_id();
                Users u = userService.findById(userId).get();
                Driver d;
                if (driverService.findById(userId).isPresent()) {
                    d = driverService.findById(userId).get();
                } else {
                    d = null;
                }

                Map<String, Object> m = new HashMap<>();

                if (d != null) {
                    Map<String, Object> s = new HashMap<>();
                    Map<String, Object> c = new HashMap<>();
                    Vehicle v = vehicleService.findByuserId(userId);
                    s.put("bankAccount", "");
                    s.put("status", String.valueOf(d.getIsBusy()));
                    s.put("updatePending", "0");
                    s.put("imageExtra", u.getLinkImage());
                    s.put("imageExtra2", u.getLinkImage());
                    s.put("isActive", String.valueOf(u.getStatus()));
                    s.put("lat", String.valueOf(d.getLatitude()));
                    s.put("long", String.valueOf(d.getLongitude()));
                    s.put("driverRate", String.valueOf(d.getRate()));
                    s.put("driverRateCount", "0");
                    s.put("isOnline", String.valueOf(d.getIsOnline()));
                    s.put("linkType", "1");
                    s.put("carPlate", "8888");
                    s.put("carType", "1");
                    s.put("vehiclePlate", v.getCarPlate());
                    s.put("vehicleType", String.valueOf(v.getModel()));

                    c.put("id", "2");
                    c.put("vehiclePlate", v.getCarPlate());
                    c.put("vehicleType", String.valueOf(v.getModel()));
                    c.put("dateCreated", "2019");
                    c.put("image1", "");
                    c.put("image2", "");

                    m.put("shop", s);
                    m.put("car", c);
                } else {
                    m.put("shop", null);
                    m.put("car", null);
                }

                m.put("id", String.valueOf(u.getId()));
                m.put("fullName", u.getFullName());
                m.put("image", u.getLinkImage());
                m.put("email", u.getEmail());
                m.put("description", "");
                m.put("isActive", String.valueOf(u.getStatus()));
                m.put("gender", null);
                m.put("phone", u.getPhone());
                m.put("dob", "");
                m.put("address", "Ha Noi");
                m.put("balance", "10000.0");
                m.put("isOnline", String.valueOf(u.getStatus()));
                m.put("rate", String.valueOf(u.getRate()));
                m.put("rateCount", String.valueOf(u.getRateCount()));
                m.put("passengerRate", String.valueOf(u.getRate()));
                m.put("passengerRateCount", String.valueOf(u.getRateCount()));
                m.put("stateId", "0");
                m.put("stateName", "");
                m.put("cityId", "");
                m.put("cityName", "");
                m.put("typeAccount", "0");
                m.put("typeTasker", String.valueOf(u.getTypeTasker()));
                m.put("account", "");

                return Constants.JsonResponse(Constants.SUCCESS, m, "OK", null);
            }
        }
    }

    @RequestMapping(value = "/online", method = RequestMethod.POST)
    public ResponseEntity online(String token, String status, String lat, @RequestParam("long") String lon) throws JsonProcessingException {
        LoginToken log = logService.findByToken(token);
        Integer userId = log.getUser_id();
        Driver u = driverService.findByuserId(userId);
        u.setIsOnline(Integer.valueOf(status));
        u.setLatitude(lat);
        u.setLongitude(lon);
        driverService.save(u);
        System.out.println("Online");
        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }

    @RequestMapping(value = "/updateProfile/", method = RequestMethod.GET)
    public ResponseEntity<Users> updateProfile(
            @RequestParam String full_name,
            String token,
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
        
        LoginToken l = logService.findByToken(token);
        Users u = userService.findById(l.getUser_id()).get();
        String filename = Constants.uploadFile(image);
        u.setEmail(email);
        u.setPassword(Constants.encryptMD5(password));
        u.setFullName(full_name);
        u.setDateCreated(Constants.getTimeStamp());
        u.setImage(filename);
        u.setGender(1);
        u.setPhone(phone);
        u.setStatus(Constants.STT_ACTIVE);
        u.setRate(10.0);
        u.setRateCount(0);
        u.setTypeTasker(3);
        userService.save(u);
        System.out.println("Update profile OK");
        return Constants.JsonResponse(Constants.SUCCESS, "", "Cập nhật thành công", null);
    }

    @RequestMapping(value = "/registerShop", method = RequestMethod.POST)
    public ResponseEntity<Users> registerShop(
            HttpServletRequest req,
            String token,
            String fullName,
            String phone,
            String vehicleType,
            String driverType,
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

        Optional<Driver> check = driverService.findById(l.getUser_id());

        if (check.isPresent()) {
            return Constants.JsonResponse(Constants.ERROR, "", "Driver đã tồn tại!", null);
        }

        Users u = userService.findById(l.getUser_id()).get();

        if (avatar == null) {
            String oldAvatar = u.getImage();
            u.setImage(oldAvatar);
        } else {
            String avt = Constants.uploadFile(avatar);
            u.setImage(avt);
        }
        u.setTypeTasker(2);
        userService.save(u);

        String image = Constants.uploadFile(imageExtra);
        String image2 = Constants.uploadFile(imageExtra2);
        Vehicle newV = new Vehicle(u.getId(), vehiclePlate, vehicleType, 1, 1, "", Constants.getTimeStamp(), image, image2);
        vehicleService.save(newV);
        Driver newD = new Driver(u.getId(), Constants.DRIVER_OFFLINE, Constants.DRIVER_IDLE, 10.0, 0, "1");
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

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity forgotPassword(String email) throws JsonProcessingException {

        Users u = userService.findByEmail(email);

        if (u == null) {
            return Constants.JsonResponse(Constants.ERROR, "", "Email không tồn tại", null);
        }
        String newPass = Constants.generateString(10);
        String newPassHash = Constants.encryptMD5(newPass);
        u.setPassword(newPassHash);
        userService.save(u);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Xin chào, đây là mật khẩu mới của bạn");
        msg.setText(newPass);

        mail.send(msg);
        return Constants.JsonResponse(Constants.SUCCESS, "", "Gửi email thành công", null);
    }
}
