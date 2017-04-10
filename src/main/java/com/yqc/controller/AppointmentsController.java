package com.yqc.controller;

import com.yqc.entity.User;
import com.yqc.service.AppointmentBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangqc on 2017/3/27.
 */
@RestController
@RequestMapping
public class AppointmentsController {

    private final AppointmentBook appointmentBook;

    @Autowired
    public AppointmentsController(AppointmentBook appointmentBook) {
        this.appointmentBook = appointmentBook;
    }

    @RequestMapping("/index")
    public String index() {
        return "demo";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> manyUsersXml() {
        List<User> userList = new ArrayList<>(1000);
        for (int i = 0; i < 100000; i++) {
            userList.add(new User("yangqc" + i, i, "dog" + i));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
                .headers(headers)
                .body(userList);
    }

    @GetMapping("/users_no_cache")
    public ResponseEntity<List<User>> manyUsersXmlNoCache() {
        List<User> userList = new ArrayList<>(1000);
        for (int i = 0; i < 100000; i++) {
            userList.add(new User("yangqc" + i, i, "dog" + i));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return ResponseEntity.ok()
                .headers(headers)
                .body(userList);
    }

}
