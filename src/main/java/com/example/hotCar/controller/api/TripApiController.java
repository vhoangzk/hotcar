/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hotCar.controller.api;

import com.example.hotCar.model.Driver;
import com.example.hotCar.model.LoginToken;
import com.example.hotCar.model.Request;
import com.example.hotCar.model.Trip;
import com.example.hotCar.model.Users;
import com.example.hotCar.service.DriverService;
import com.example.hotCar.service.LoginTokenService;
import com.example.hotCar.service.RequestService;
import com.example.hotCar.service.TripService;
import com.example.hotCar.service.UserService;
import com.example.hotCar.until.Constants;
import com.example.hotCar.until.FCMNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class TripApiController {

    @Autowired
    LoginTokenService log;
    @Autowired
    TripService trip;
    @Autowired
    DriverService driverSer;
    @Autowired
    UserService user;
    @Autowired
    RequestService rSer;

    @RequestMapping(value = "/showTripDetail", method = RequestMethod.POST)
    public ResponseEntity showTripDetail(String token, Integer tripId) throws JsonProcessingException {
        LoginToken l = log.findByToken(token);
        Trip t = trip.findById(tripId).get();
        if (t == null) {
            return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
        }
//        Trip t = check.get();
        Driver d = driverSer.findByuserId(t.getDriverId());
        Users u = user.findById(d.getUserId()).get();
        Users p = user.findById(t.getPassengerId()).get();

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> driver = new HashMap<>();
        Map<String, Object> passenger = new HashMap<>();
        Map<String, Object> product = new HashMap<>();

        data.put("totalTime", 0);
        data.put("totalPrice", t.getActualFare());
        data.put("pickUpAtA", "0");
        data.put("workAtB", "0");
        data.put("startTimeWorking", "1573112367");
        data.put("endTimeWorking", "1572855533");

        data.put("id", String.valueOf(t.getId()));
        data.put("passengerId", String.valueOf(t.getPassengerId()));
        data.put("link", "");
        data.put("startTime", String.valueOf(t.getStartTime()));
        data.put("startLat", t.getStartLat());
        data.put("startLong", t.getStartLong());
        data.put("endLat", t.getEndLat());
        data.put("endLong", t.getEndLong());
        data.put("dateCreated", String.valueOf(t.getEndTime()));
        data.put("driverId", String.valueOf(t.getDriverId()));
        data.put("startLocation", t.getStartLocation());
        data.put("endLocation", t.getEndLocation());
        data.put("status", String.valueOf(t.getStatus()));
        data.put("endTime", String.valueOf(t.getEndTime()));
        data.put("distance", String.valueOf(t.getDistance()));
        data.put("estimateFare", String.valueOf(t.getEstimateFare()));
        data.put("actualFare", String.valueOf(t.getActualFare()));
        data.put("driverRate", String.valueOf((t.getDriverRate().intValue())));
        data.put("passengerRate", String.valueOf(t.getPassengerRate().intValue()));

        driver.put("driverName", u.getFullName());
        driver.put("identity", "");
        driver.put("rate", String.valueOf(d.getRate()));
        driver.put("rateCount", String.valueOf(d.getRateCount()));
        driver.put("imageDriver", u.getLinkImage());
        driver.put("carPlate", "8888");
        driver.put("carImage", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");
        driver.put("phone", u.getPhone());

        passenger.put("id", String.valueOf(p.getId()));
        passenger.put("passengerName", p.getFullName());
        passenger.put("rate", String.valueOf(p.getRate()));
        passenger.put("rateCount", String.valueOf(p.getRateCount()));
        passenger.put("imagePassenger", p.getLinkImage());
        passenger.put("phone", p.getPhone());

        product.put("id", "1");
        product.put("shopName", "");
        product.put("shopImage", "");
        product.put("categoryId", "");
        product.put("categoryName", "");
        product.put("marker", "");
        product.put("phone", "");
        product.put("productName", "");
        product.put("productSize", "");
        product.put("description", "");
        product.put("image", "");
        product.put("rate", "");
        product.put("rateCount", "");
        product.put("quantity", "");
        product.put("price", "");
        product.put("shipFee", "");
        product.put("rateShop", "");
        product.put("rateCountShop", "");

        data.put("driver", driver);
        data.put("passenger", passenger);
        data.put("product", product);

        return Constants.JsonResponse(Constants.SUCCESS, data, "OK", null);
    }

    @RequestMapping(value = "/updateCoordinate", method = RequestMethod.POST)
    public ResponseEntity updateCoordinate(String token, String lat, @RequestParam("long") String lon) throws JsonProcessingException {
        Driver d = driverSer.findByuserId(log.findByToken(token).getUser_id());
        d.setLatitude(lat);
        d.setLongitude(lon);
        driverSer.save(d);
        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }

    @RequestMapping(value = "/endTrip", method = RequestMethod.POST)
    public ResponseEntity endTrip(String token, Integer tripId) throws JsonProcessingException {
        LoginToken l = log.findByToken(token);
        Trip t = trip.findById(tripId).get();
        Driver d = driverSer.findByuserId(t.getDriverId());
        t.setStatus(Constants.TRIP_STATUS_FINISH);
        trip.save(t);
        d.setIsBusy(Constants.DRIVER_IDLE);
        driverSer.save(d);
        System.out.println("Kết thúc chuyến thứ: " + t.getId());

        LoginToken l2 = log.findByUserId(t.getPassengerId());
        ArrayList<String> key = new ArrayList<>();
        key.add(l2.getToken());
        FCMNotification.push(new ArrayList(), "endTrip", "Chuyen di da hoan thanh", key);

        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }

    @RequestMapping(value = "/startTrip", method = RequestMethod.POST)
    public ResponseEntity startTrip(String token, Integer tripId) throws JsonProcessingException {
        LoginToken l = log.findByToken(token);
        Trip t = trip.findById(tripId).get();
        Driver d = driverSer.findByuserId(t.getDriverId());
        t.setStatus(Constants.TRIP_STATUS_INPROGRESS);
        trip.save(t);
        d.setIsBusy(Constants.DRIVER_BUSY);
        driverSer.save(d);
        System.out.println("Bắt đầu chuyến thứ: " + t.getId());

        LoginToken l2 = log.findByUserId(t.getPassengerId());
        ArrayList<String> key = new ArrayList<>();
        key.add(l2.getToken());
        FCMNotification.push(new ArrayList(), "startTrip", "Chuyến đi đã bắt đầu", key);

        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }

    @RequestMapping(value = "/ratePassenger", method = RequestMethod.POST)
    public ResponseEntity ratePassenger(Double rate, Integer tripId, String token) throws JsonProcessingException {
        LoginToken l = log.findByToken(token);
        Trip t = trip.findById(tripId).get();
        Users p = user.findById(t.getPassengerId()).get();
        Integer pRateCount = p.getRateCount();
        Integer rateCount = pRateCount + 1;
        if (pRateCount == 0) {
            p.setRate(rate);
        } else {
            Double pRate = p.getRate();
            Double pRate2 = pRate + rate / rateCount;
            p.setRate(pRate2);
        }
        p.setRateCount(rateCount);
        user.save(p);
        System.out.println("Rate Passenger: " + rate);
        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }

    @RequestMapping(value = "/rateDriver", method = RequestMethod.POST)
    public ResponseEntity rateDriver(Double rate, Integer tripId, String token) throws JsonProcessingException {
        LoginToken l = log.findByToken(token);
        Trip t = trip.findById(tripId).get();
        Driver d = driverSer.findById(t.getDriverId()).get();
        Integer pRateCount = d.getRateCount();
        Integer rateCount = pRateCount + 1;
        if (pRateCount == 0) {
            d.setRate(rate);
        } else {
            Double pRate = d.getRate();
            Double pRate2 = pRate + rate / rateCount;
            d.setRate(pRate2);
        }
        d.setRateCount(rateCount);
        driverSer.save(d);
        System.out.println("Rate Driver: " + rate);
        return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
    }

    @RequestMapping(value = "/showMyTrip", method = RequestMethod.GET)
    public ResponseEntity showMyTrip(String token, Integer page) throws JsonProcessingException {
        LoginToken l = log.findByToken(token);

//        ArrayList<Trip> allTrip = trip.findMyTrip(l.getUser_id());
//        Integer size = (int) allTrip.size() / 10;
//        Integer offset = (page - 1) * size;
//        System.out.println("All trips: " + allTrip.size());
        ArrayList<Trip> ts = trip.findMyTrip(l.getUser_id());
        System.out.println("Trips: " + ts.size());
        if (ts.isEmpty()) {
            return Constants.JsonResponse(Constants.SUCCESS, "", "OK", null);
        }
        ArrayList<Map> arrRtn = new ArrayList<>();
        ts.forEach((t) -> {
            Map<String, Object> data = new HashMap<>();
            Driver d = driverSer.findByuserId(t.getDriverId());
            Users u = user.findById(d.getUserId()).get();
            Users p = user.findById(t.getPassengerId()).get();

            Map<String, Object> driver = new HashMap<>();
            Map<String, Object> passenger = new HashMap<>();

            data.put("id", String.valueOf(t.getId()));
            data.put("passengerId", String.valueOf(t.getPassengerId()));
            data.put("link", "");
            data.put("startTime", String.valueOf(t.getStartTime()));
            data.put("startLat", t.getStartLat());
            data.put("startLong", t.getStartLong());
            data.put("endLat", t.getEndLat());
            data.put("endLong", t.getEndLong());
            data.put("dateCreated", String.valueOf(t.getEndTime()));
            data.put("driverId", String.valueOf(t.getDriverId()));
            data.put("startLocation", t.getStartLocation());
            data.put("endLocation", t.getEndLocation());
            data.put("status", String.valueOf(t.getStatus()));
            data.put("endTime", String.valueOf(t.getEndTime()));
            data.put("distance", String.valueOf(t.getDistance()));
            data.put("estimateFare", String.valueOf(t.getEstimateFare()));
            data.put("actualFare", String.valueOf(t.getActualFare()));
            data.put("driverRate", String.valueOf(t.getDriverRate()));
            data.put("passengerRate", String.valueOf(t.getPassengerRate()));

            driver.put("driverName", u.getFullName());
            driver.put("identity", "");
            driver.put("rate", String.valueOf(d.getRate()));
            driver.put("rateCount", String.valueOf(d.getRateCount()));
            driver.put("imageDriver", u.getLinkImage());
            driver.put("carPlate", "8888");
            driver.put("carImage", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");
            driver.put("phone", u.getPhone());

            passenger.put("id", String.valueOf(p.getId()));
            passenger.put("passengerName", p.getFullName());
            passenger.put("rate", String.valueOf(p.getRate()));
            passenger.put("rateCount", String.valueOf(p.getRateCount()));
            passenger.put("imagePassenger", p.getLinkImage());
            passenger.put("phone", p.getPhone());

            data.put("driver", driver);
            data.put("passenger", passenger);
            arrRtn.add(data);
        });

        return Constants.JsonResponse(Constants.SUCCESS, arrRtn, "OK", null);
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public ResponseEntity changeStatus(String token, Integer status, Integer tripId) throws JsonProcessingException {

        LoginToken l = log.findByToken(token);

        Trip t = trip.findById(tripId).get();
        Driver d = driverSer.findByuserId(t.getDriverId());
        Users u = user.findById(d.getUserId()).get();
        Users p = user.findById(t.getPassengerId()).get();

        t.setStatus(status);
        trip.save(t);
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> driver = new HashMap<>();
        Map<String, Object> passenger = new HashMap<>();
        Map<String, Object> product = new HashMap<>();

        data.put("id", String.valueOf(t.getId()));
        data.put("passengerId", String.valueOf(t.getPassengerId()));
        data.put("link", "");
        data.put("startTime", String.valueOf(t.getStartTime()));
        data.put("startLat", t.getStartLat());
        data.put("startLong", t.getStartLong());
        data.put("endLat", t.getEndLat());
        data.put("endLong", t.getEndLong());
        data.put("dateCreated", String.valueOf(t.getEndTime()));
        data.put("driverId", String.valueOf(t.getDriverId()));
        data.put("startLocation", t.getStartLocation());
        data.put("endLocation", t.getEndLocation());
        data.put("status", String.valueOf(t.getStatus()));
        data.put("endTime", String.valueOf(t.getEndTime()));
        data.put("distance", String.valueOf(t.getDistance()));
        data.put("estimateFare", String.valueOf(t.getEstimateFare()));
        data.put("actualFare", String.valueOf(t.getActualFare()));
        data.put("driverRate", String.valueOf(t.getDriverRate()));
        data.put("passengerRate", String.valueOf(t.getPassengerRate()));

        driver.put("driverName", u.getFullName());
        driver.put("identity", "");
        driver.put("rate", String.valueOf(d.getRate()));
        driver.put("rateCount", String.valueOf(d.getRateCount()));
        driver.put("imageDriver", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");
        driver.put("carPlate", "8888");
        driver.put("carImage", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");
        driver.put("phone", u.getPhone());

        passenger.put("id", String.valueOf(p.getId()));
        passenger.put("passengerName", p.getFullName());
        passenger.put("rate", String.valueOf(p.getRate()));
        passenger.put("rateCount", String.valueOf(p.getRateCount()));
        passenger.put("imagePassenger", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");
        passenger.put("phone", p.getPhone());

        product.put("id", "1");
        product.put("shopName", "");
        product.put("shopImage", "");
        product.put("categoryId", "");
        product.put("category", "");
        product.put("phone", "");
        product.put("productName", "");
        product.put("productSize", "");
        product.put("quantity", "");
        product.put("price", "");
        product.put("shipFee", "");
        product.put("totalPrice", "");
        product.put("quantity", "");
        product.put("address", "");
        product.put("rate", "");
        product.put("rateCount", "");
        product.put("image", "");

        data.put("driver", driver);
        data.put("passenger", passenger);
        data.put("product", product);
        String message = "Change status";
//        switch (status) {
//            case Constants.TRIP_STATUS_APPROACHING:
//                message = "Tài xế đã tới điểm đón";
//                break;
//            case Constants.TRIP_STATUS_INPROGRESS:
//                message = "Tài xế đã tới điểm trả";
//                break;
//            case Constants.TRIP_STATUS_START_TASK:
//                message = "Tài xế đã bắt đầu chuyến đi";
//                break;
//        }
        LoginToken l2 = log.findByUserId(t.getPassengerId());
        ArrayList<String> key = new ArrayList<>();
        key.add(l2.getToken());
        FCMNotification.push(new ArrayList(), String.valueOf(status), message, key);
        System.out.println("Change status: " + status);
        System.out.println("Push to: " + p.getEmail());
        return Constants.JsonResponse(Constants.SUCCESS, data, "OK", null);
    }

    @RequestMapping(value = "/driverConfirm", method = RequestMethod.POST)
    public ResponseEntity driverConfirm(String token, Integer requestId, Double startLat, Double startLong, String startLocation) throws JsonProcessingException, Exception {
        LoginToken l = log.findByToken(token);
        Request r = rSer.findById(requestId).get();
        Driver d = driverSer.findByuserId(r.getDriverId());
        Users p = user.findById(r.getPassengerId()).get();
        Users u = user.findById(r.getDriverId()).get();
        Double distance = Constants.distance(Double.valueOf(r.getStartLat()), Double.valueOf(r.getStartLong()), Double.valueOf(r.getEndLat()), Double.valueOf(r.getEndLong()), "K");
        Trip t = new Trip(
                r.getPassengerId(),
                r.getDriverId(),
                r.getVehicleType(),
                Constants.getTimeStamp(),
                Constants.getTimeStamp(),
                r.getStartLat(),
                r.getStartLong(),
                r.getStartLocation(),
                r.getEndLat(),
                r.getEndLong(),
                r.getEndLocation(),
                String.valueOf(distance),
                Constants.TRIP_STATUS_APPROACHING,
                Constants.estimateFare(distance),
                Constants.estimateFare(distance),
                d.getRate(),
                p.getRate()
        );
        d.setIsBusy(Constants.DRIVER_BUSY);
        driverSer.save(d);
        trip.save(t);
        rSer.deleteAllBypassengerId(t.getPassengerId());
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> driver = new HashMap<>();
        Map<String, Object> passenger = new HashMap<>();
        Map<String, Object> product = new HashMap<>();

        data.put("id", String.valueOf(t.getId()));
        data.put("passengerId", String.valueOf(t.getPassengerId()));
        data.put("link", "");
        data.put("startTime", String.valueOf(t.getStartTime()));
        data.put("startLat", t.getStartLat());
        data.put("startLong", t.getStartLong());
        data.put("endLat", t.getEndLat());
        data.put("endLong", t.getEndLong());
        data.put("dateCreated", String.valueOf(t.getEndTime()));
        data.put("driverId", String.valueOf(t.getDriverId()));
        data.put("startLocation", t.getStartLocation());
        data.put("endLocation", t.getEndLocation());
        data.put("status", String.valueOf(t.getStatus()));
        data.put("endTime", String.valueOf(t.getEndTime()));
        data.put("distance", String.valueOf(t.getDistance()));
        data.put("estimateFare", String.valueOf(t.getEstimateFare()));
        data.put("actualFare", String.valueOf(t.getActualFare()));
        data.put("driverRate", String.valueOf(t.getDriverRate()));
        data.put("passengerRate", String.valueOf(t.getPassengerRate()));

        driver.put("driverName", u.getFullName());
        driver.put("identity", "");
        driver.put("rate", String.valueOf(d.getRate()));
        driver.put("rateCount", String.valueOf(d.getRateCount()));
        driver.put("imageDriver", u.getLinkImage());
        driver.put("carPlate", "8888");
        driver.put("carImage", "http://bestapp.site/graduationproject/upload/job_type/motobike.png");
        driver.put("phone", u.getPhone());

        passenger.put("id", String.valueOf(p.getId()));
        passenger.put("passengerName", p.getFullName());
        passenger.put("rate", String.valueOf(p.getRate()));
        passenger.put("rateCount", String.valueOf(p.getRateCount()));
        passenger.put("imagePassenger", p.getLinkImage());
        passenger.put("phone", p.getPhone());

        product.put("id", "1");
        product.put("shopName", "");
        product.put("shopImage", "");
        product.put("categoryId", "");
        product.put("category", "");
        product.put("phone", "");
        product.put("productName", "");
        product.put("productSize", "");
        product.put("quantity", "");
        product.put("price", "");
        product.put("shipFee", "");
        product.put("totalPrice", "");
        product.put("quantity", "");
        product.put("address", "");
        product.put("rate", "");
        product.put("rateCount", "");
        product.put("image", "");

        data.put("driver", driver);
        data.put("passenger", passenger);
        data.put("product", product);

        JSONObject data1 = new JSONObject();
        Map<String, String> m = new HashMap<>();
        m.put("tripId", String.valueOf(t.getId()));
        data1.put("data", m);
        data1.put("action", "driverConfirm");
        data1.put("trip_status", String.valueOf(t.getStatus()));
        data1.put("body", "Tài xế đã nhận chuyến");
        LoginToken l2 = log.findByUserId(t.getPassengerId());
        FCMNotification.pushFCMNotification(l2.getToken(), data1);
        
        System.out.println("Start location: " + startLocation);
        return Constants.JsonResponse(Constants.SUCCESS, data, "OK", null);
    }

    @RequestMapping(value = "/driverConfirmPayment", method = RequestMethod.GET)
    public ResponseEntity driverConfirmPayment(String token, Integer status, Integer tripId) throws JsonProcessingException {
        return Constants.JsonResponse(Constants.SUCCESS, 0, "OK", null);
    }
}
