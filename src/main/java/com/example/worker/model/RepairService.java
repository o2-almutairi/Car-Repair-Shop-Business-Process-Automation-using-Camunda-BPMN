package com.example.worker.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairService {
    private Long repairServiceId;
    private String serviceDetails;
    private Date serviceDate;
    private Double cost;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Vehicle vehicle;
}
