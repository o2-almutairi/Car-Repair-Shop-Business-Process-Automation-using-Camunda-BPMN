package com.example.worker.model;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Benefit {
    private Long benefitId;
    private String description;
    private Double discountRate;
    private Boolean appliesToAll;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<MembershipBenefit> membershipBenefits;
}