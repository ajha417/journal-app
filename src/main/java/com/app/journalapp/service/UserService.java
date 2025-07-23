package com.app.journalapp.service;

import com.app.journalapp.entity.Users;
import com.app.journalapp.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(Users users) {
        users.setRoles(new ArrayList<>(List.of("ROLE_USER"))
        );
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepo.save(users);
    }

    public Users findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Users findById(long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Transactional
    public void deleteUser(String username) {
        userRepo.deleteByUsername(username);
    }

    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }
}
