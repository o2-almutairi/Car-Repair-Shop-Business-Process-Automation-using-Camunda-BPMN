package com.example.worker.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipBenefit {
    private Long id;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Membership membership;
    private Benefit benefit;
}
