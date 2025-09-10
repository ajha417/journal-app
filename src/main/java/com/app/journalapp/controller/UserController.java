package com.app.journalapp.controller;

import com.app.journalapp.entity.Users;
import com.app.journalapp.rest.dto.WeatherDto;
import com.app.journalapp.service.UserService;
import com.app.journalapp.service.WeatherService;
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
    private final WeatherService weatherService;

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

    @GetMapping("/greet")
    public String greetUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WeatherDto weatherDto = weatherService.getWeather("Ahmedabad");
        Users user = userService.findByUsername(username);
        if (weatherDto != null) {
            return "Hello " + user.getUsername() + " The temperature in your city is " + weatherDto.getCurrent().getTemperature()
                    + " and the it feelsLike" + weatherDto.getCurrent().getFeelsLike() + " and the direction of wind is " +
                    weatherDto.getCurrent().getWindDir();
        }
        return "Hello " + user.getUsername() + "!";
    }
}
