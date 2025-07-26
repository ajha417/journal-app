package com.app.journalapp.service;

import com.app.journalapp.entity.Users;
import com.app.journalapp.repo.UserRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSaveUser() {
        Users users = new Users();
        users.setUsername("testuser");
        users.setPassword("123");
        Mockito.when(passwordEncoder.encode(users.getPassword())).thenReturn("123");
        Mockito.when(userRepo.save(Mockito.any(Users.class))).thenReturn(users);

        userService.saveUser(users);
        Mockito.verify(userRepo, Mockito.times(1)).save(users);
    }

    @ParameterizedTest
    @CsvSource({
            "1,,1,2",
            "2,1,3"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }
}
