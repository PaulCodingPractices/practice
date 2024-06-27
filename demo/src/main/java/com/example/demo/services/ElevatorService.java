package com.example.demo.services;

import com.example.demo.entities.Elevator;
import com.example.demo.entities.Request;
import com.example.demo.entities.User;
import com.example.demo.events.ElevatorEvent;
import com.example.demo.events.StepEvent;
import com.example.demo.repositories.ElevatorRepository;
import com.example.demo.repositories.RequestRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class ElevatorService {

    @Autowired
    private ElevatorRepository elevatorRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private Queue<Request> requestQueue = new LinkedList<>();

    public void pickup(int pickupFloor, int destinationFloor, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        Elevator nearestElevator = findNearestElevator(pickupFloor);
        if (nearestElevator != null) {
            if (nearestElevator.getPassengerCount() < nearestElevator.getCapacity()) {
                nearestElevator.getRequests().add(new Request(pickupFloor, destinationFloor, userId));
                nearestElevator.setMoving(true);
                eventPublisher.publishEvent(new ElevatorEvent(this, nearestElevator));
                elevatorRepository.save(nearestElevator);
            } else {
                handleFullElevator(new Request(pickupFloor, destinationFloor, userId));
            }
        } else {
            handleFullElevator(new Request(pickupFloor, destinationFloor, userId));
        }
    }

    private void handleFullElevator(Request request) {
        requestQueue.add(request);
        notifyUser(request.getUserId(), "All elevators are currently full. Your request has been queued.");
    }

    public void processQueuedRequests() {
        Request request;
        while ((request = requestQueue.poll()) != null) {
            pickup(request.getPickupFloor(), request.getDestinationFloor(), request.getUserId());
        }
    }

    private void notifyUser(Long userId, String message) {
        System.out.println("User " + userId + ": " + message);
    }

    public void update(Long elevatorId, int currentFloor, int targetFloor) {
        Elevator elevator = elevatorRepository.findById(elevatorId).orElseThrow();
        elevator.setCurrentFloor(currentFloor);
        elevator.setTargetFloor(targetFloor);
        elevator.setMoving(true);
        elevatorRepository.save(elevator);
    }

    public void step() {
        eventPublisher.publishEvent(new StepEvent(this));
    }

    public List<Elevator> status() {
        return elevatorRepository.findAll();
    }

    private Elevator findNearestElevator(int pickupFloor) {
        List<Elevator> elevators = elevatorRepository.findAll();
        Elevator nearestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - pickupFloor);
            if (distance < minDistance) {
                minDistance = distance;
                nearestElevator = elevator;
            }
        }

        return nearestElevator;
    }
}
