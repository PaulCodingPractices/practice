package com.example.demo.services;

import com.example.demo.entities.Elevator;
import com.example.demo.entities.Request;
import com.example.demo.events.ElevatorEvent;
import com.example.demo.events.StepEvent;
import com.example.demo.repositories.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ElevatorScheduler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(16); // Adjust pool size as needed

    @Autowired
    private ElevatorRepository elevatorRepository;

    @EventListener
    public void handleElevatorEvent(ElevatorEvent event) {
        Elevator elevator = event.getElevator();
        scheduler.scheduleAtFixedRate(new ElevatorTask(elevator), 0, 1, TimeUnit.SECONDS);
    }

    @EventListener
    public void handleStepEvent(StepEvent event) {
        scheduler.scheduleAtFixedRate(this::batchProcess, 0, 5, TimeUnit.SECONDS);
    }

    private void batchProcess() {
        List<Elevator> elevators = elevatorRepository.findAll();
        for (Elevator elevator : elevators) {
            if (elevator.isMoving()) {
                new ElevatorTask(elevator).run();
            }
        }
    }

    private class ElevatorTask implements Runnable {
        private final Elevator elevator;

        public ElevatorTask(Elevator elevator) {
            this.elevator = elevator;
        }

        @Override
        public void run() {
            if (elevator.isMoving()) {
                moveElevator();
                processRequests();
                elevatorRepository.save(elevator); // Save the updated state
            }
        }

        private void moveElevator() {
            // Move elevator one step towards its target
            if (elevator.getCurrentFloor() < elevator.getTargetFloor()) {
                elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
            } else if (elevator.getCurrentFloor() > elevator.getTargetFloor()) {
                elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
            } else {
                elevator.setMoving(false);
            }
        }

        private void processRequests() {
            // Process requests in the queue
            if (!elevator.getRequests().isEmpty()) {
                Request currentRequest = elevator.getRequests().peek();
                if (elevator.getCurrentFloor() == currentRequest.getPickupFloor()) {
                    elevator.setTargetFloor(currentRequest.getDestinationFloor());
                    elevator.getRequests().poll(); // Remove the processed request
                    elevator.setPassengerCount(elevator.getPassengerCount() + 1);
                }
            }
        }
    }
}
