package com.example.demo.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pickup_floor", nullable = false)
    private int pickupFloor;

    @Column(name = "destination_floor", nullable = false)
    private int destinationFloor;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "elevator_id")
    private Elevator elevator;

    // Constructor
    public Request(int pickupFloor, int destinationFloor, Long userId) {
        this.pickupFloor = pickupFloor;
        this.destinationFloor = destinationFloor;
        this.userId = userId;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getPickupFloor() { return pickupFloor; }
    public void setPickupFloor(int pickupFloor) { this.pickupFloor = pickupFloor; }
    public int getDestinationFloor() { return destinationFloor; }
    public void setDestinationFloor(int destinationFloor) { this.destinationFloor = destinationFloor; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}