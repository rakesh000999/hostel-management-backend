package com.hostel.management.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Room number cannot be empty")
    @Pattern(
            regexp = "^[A-Za-z0-9-]+$",
            message = "Room number can contain only letters, numbers, and hyphens"
    )
    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    @Positive (message = "Capacity must be greater than zero")
    private int capacity;

    @PositiveOrZero(message = "Occupied count cannot be negative")
    @Column(name = "occupied_count")
    private int occupiedCount;

    @DecimalMin(value = "0.0", inclusive = true, message = "Rent cannot be negative")
    @Column(name = "rent_per_month")
    private double rentPerMonth;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupiedCount() {
        return occupiedCount;
    }

    public void setOccupiedCount(int occupiedCount) {
        this.occupiedCount = occupiedCount;
    }

    public double getRentPerMonth() {
        return rentPerMonth;
    }

    public void setRentPerMonth(double rentPerMonth) {
        this.rentPerMonth = rentPerMonth;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", capacity=" + capacity +
                ", occupiedCount=" + occupiedCount +
                ", rentPerMonth=" + rentPerMonth +
                '}';
    }
}
