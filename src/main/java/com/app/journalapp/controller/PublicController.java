package com.app.journalapp.controller;

import com.app.journalapp.cache.AppCache;
import com.app.journalapp.entity.Users;
import com.app.journalapp.service.UserDetailsServiceImpl;
import com.app.journalapp.service.UserService;
import com.app.journalapp.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users users) {
        log.debug("login()::Enter");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(users.getUsername());
            String jwtToken = jwtUtils.generateJwtToken(userDetails.getUsername());
            log.debug("login()::Exit");
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error logging in user", e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/clearAppCache")
    public void clearAppCache() {
        appCache.init();
    }
}
