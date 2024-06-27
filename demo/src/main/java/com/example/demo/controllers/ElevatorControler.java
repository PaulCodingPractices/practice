package com.example.demo.controllers;

import com.example.demo.entities.Elevator;
import com.example.demo.services.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elevators")
public class ElevatorControler {

    @Autowired
    private ElevatorService elevatorService;

    @PostMapping("/pickup")
    public void pickup(@RequestParam int pickupFloor, @RequestParam int destinationFloor, @RequestParam Long userId) {
        elevatorService.pickup(pickupFloor, destinationFloor, userId);
    }

    @PostMapping("/update")
    public void update(@RequestParam long elevatorId, @RequestParam int currentFloor, @RequestParam int targetFloor) {
        elevatorService.update(elevatorId, currentFloor, targetFloor);
    }

    @PostMapping("/step")
    public void step() {
        elevatorService.step();
    }

    @GetMapping("/status")
    public List<Elevator> status() {
        return elevatorService.status();
    }
}
