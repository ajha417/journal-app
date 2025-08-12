package com.app.journalapp.controller;

import com.app.journalapp.cache.AppCache;
import com.app.journalapp.entity.Users;
import com.app.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/health")
    public String healthCheck() {
        return "working";
    }

    @PostMapping("/addUser")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        try {
            userService.saveUser(users);
            return new ResponseEntity<>(users, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/clearAppCache")
    public void clearAppCache() {
        appCache.init();
    }
}
