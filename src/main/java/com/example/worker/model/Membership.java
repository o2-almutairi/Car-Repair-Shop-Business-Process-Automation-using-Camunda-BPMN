package com.example.worker.model;

import lombok.*;

import java.util.Date;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership {
    private Long membershipId;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;
    private Date membershipStartDate;
    private Date membershipEndDate;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<MembershipBenefit> membershipBenefits;
}
