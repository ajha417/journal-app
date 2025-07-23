package com.app.journalapp.controller;

import com.app.journalapp.entity.Users;
import com.app.journalapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping( "/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody Users users) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users existingUser = userService.findByUsername(username);
        existingUser.setUsername(users.getUsername());
        existingUser.setPassword(users.getPassword());
        userService.saveUser(existingUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
