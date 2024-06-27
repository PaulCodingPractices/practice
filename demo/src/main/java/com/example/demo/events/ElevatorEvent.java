package com.example.demo.events;

import com.example.demo.entities.Elevator;
import org.springframework.context.ApplicationEvent;

public class ElevatorEvent extends ApplicationEvent {

    private final Elevator elevator;

    public ElevatorEvent(Object source, Elevator elevator) {
        super(source);
        this.elevator = elevator;
    }

    public Elevator getElevator() {
        return elevator;
    }
}