package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("uniqueuser");
        user.setPassword("password");
        user.setStatus("standard");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("uniqueuser");
        assertTrue(foundUser.isPresent());
        assertEquals("uniqueuser", foundUser.get().getUsername());
    }
}
