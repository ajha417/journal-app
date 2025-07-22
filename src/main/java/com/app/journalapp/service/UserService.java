package com.app.journalapp.service;

import com.app.journalapp.entity.Users;
import com.app.journalapp.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    public void saveUser(Users users) {
        users.setRoles(List.of("ROLE_USER"));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepo.save(users);
    }

    public Users findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Users findById(long id) {
        return userRepo.findById(id).orElse(null);
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }
}
