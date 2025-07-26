package com.app.journalapp.controller;

import com.app.journalapp.entity.Users;
import com.app.journalapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        log.debug("getAllUsers()::Enter");
        ResponseEntity<?> responseEntity;
        try {
            List<Users> users = userService.getAllUsers();
            if (users.isEmpty()) {
                responseEntity = ResponseEntity.noContent().build();
            } else {
                responseEntity = ResponseEntity.ok(users);
            }
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body("Error fetching users");
        }
        log.debug("getAllUsers()::Exit");
        return responseEntity;
    }

    @PostMapping("/createAdminUser")
    public ResponseEntity<?> createAdminUser(@RequestBody Users users) {
        log.debug("createAdminUser()::Enter");
        ResponseEntity<?> responseEntity;
        try {
            userService.saveAdmin(users);
            responseEntity = ResponseEntity.ok("Admin user created successfully");
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body("Error creating admin user");
        }
        log.debug("createAdminUser()::Exit");
        return responseEntity;
    }
}
