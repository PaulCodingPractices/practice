package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        userController.getAllUsers();
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(userId);
        assertEquals(ResponseEntity.ok(user), response);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        User createdUser = userController.createUser(user);
        assertEquals(user, createdUser);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User user = new User();
        when(userService.updateUser(userId, user)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.updateUser(userId, user);
        assertEquals(ResponseEntity.ok(user), response);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(true);

        ResponseEntity<Void> response = userController.deleteUser(userId);
        assertEquals(ResponseEntity.ok().build(), response);
    }
}
