package com.example.worker.model;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private String email;
    private boolean isMembership;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Membership> memberships;
}

