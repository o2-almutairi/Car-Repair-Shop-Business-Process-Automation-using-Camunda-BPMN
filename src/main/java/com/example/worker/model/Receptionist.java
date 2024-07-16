package com.example.worker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receptionist {
    private Long receptionistId;
    private String name;
    private String phone;
    private String email;
}
