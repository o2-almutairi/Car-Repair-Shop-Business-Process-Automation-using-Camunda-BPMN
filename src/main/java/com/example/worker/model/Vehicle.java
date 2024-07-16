package com.example.worker.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private Long vehicleId;
    private String make;
    private String model;
    private Integer year;
    private String licensePlateNumber;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;
}
