package com.app.journalapp.controller;

import com.app.journalapp.entity.Users;
import com.app.journalapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/addUser")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        try {
            userService.saveUser(users);
            return new ResponseEntity<>(users, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping( "/updateUser/{username}")
    public ResponseEntity<?> updateUser(@RequestBody Users users, @PathVariable String username) {
        Users existingUser = userService.findByUsername(username);
        if (existingUser != null) {
            existingUser.setUsername(users.getUsername());
            existingUser.setPassword(users.getPassword());
            userService.saveUser(existingUser);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
