package com.example.demo.entities;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.Queue;

@Entity
@Table(name = "elevator")
public class Elevator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "current_floor", nullable = false)
    private int currentFloor;

    @Column(name = "is_moving", nullable = false)
    private boolean isMoving;

    @Column(name = "passenger_count", nullable = false)
    private int passengerCount;

    @Column(name = "target_floor", nullable = false)
    private int targetFloor;

    @OneToMany(mappedBy = "elevator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Queue<Request> requests = new LinkedList<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getCurrentFloor() { return currentFloor; }
    public void setCurrentFloor(int currentFloor) { this.currentFloor = currentFloor; }
    public int getTargetFloor() { return targetFloor; }
    public void setTargetFloor(int targetFloor) { this.targetFloor = targetFloor; }
    public boolean isMoving() { return isMoving; }
    public void setMoving(boolean moving) { isMoving = moving; }
    public int getPassengerCount() { return passengerCount; }
    public void setPassengerCount(int passengerCount) { this.passengerCount = passengerCount; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public Queue<Request> getRequests() { return requests; }
}

