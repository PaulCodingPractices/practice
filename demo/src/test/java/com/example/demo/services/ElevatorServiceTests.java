package com.example.demo.services;

import com.example.demo.entities.Elevator;
import com.example.demo.entities.User;
import com.example.demo.repositories.ElevatorRepository;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ElevatorServiceTests {

    @Mock
    private ElevatorRepository elevatorRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ElevatorService elevatorService;

    public ElevatorServiceTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPickup() {
        User user = new User();
        user.setId(1L);
        user.setStatus("standard");

        Elevator elevator = new Elevator();
        elevator.setId(1L);
        elevator.setCurrentFloor(0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(elevatorRepository.findAll()).thenReturn(List.of(elevator));
        when(elevatorRepository.save(elevator)).thenReturn(elevator);

        elevatorService.pickup(0, 5, 1L);

        assertEquals(5, elevator.getTargetFloor());
        assertEquals(true, elevator.isMoving());
        verify(elevatorRepository, times(1)).save(elevator);
    }

    @Test
    public void testUpdate() {
        Elevator elevator = new Elevator();
        elevator.setId(1L);
        elevator.setCurrentFloor(0);

        when(elevatorRepository.findById(1L)).thenReturn(Optional.of(elevator));
        when(elevatorRepository.save(elevator)).thenReturn(elevator);

        elevatorService.update(1L, 2, 5);

        assertEquals(2, elevator.getCurrentFloor());
        assertEquals(5, elevator.getTargetFloor());
        verify(elevatorRepository, times(1)).save(elevator);
    }
}
