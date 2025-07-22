package com.app.journalapp.service;

import com.app.journalapp.entity.Users;
import com.app.journalapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if (user != null) {
            return User.builder().
                    username(user.getUsername()).
                    password(user.getPassword()).
                    roles(user.getRoles().toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
