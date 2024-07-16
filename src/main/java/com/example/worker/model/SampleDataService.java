package com.example.worker.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SampleDataService {
    private final Set<Customer> customers = new HashSet<>();

    @PostConstruct
    public void initializeSampleData() {
        // Create sample benefits
        Benefit benefit1 = new Benefit(1L, "10% Discount", 10.0, true, new HashSet<>());
        Benefit benefit2 = new Benefit(2L, "Free Oil Change", 0.0, false, new HashSet<>());

        // Create sample customers
        Customer customer1 = new Customer(1L, "John Doe", "john.doe@example.com", false, new HashSet<>());
        Customer customer2 = new Customer(2L, "Jane Smith", "jane.smith@example.com", true, new HashSet<>());

        // Create sample memberships
        Membership membership1 = new Membership(1L, customer1, new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), new HashSet<>());
        Membership membership2 = new Membership(2L, customer2, new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), new HashSet<>());

        // Link memberships to customers
        customer1.getMemberships().add(membership1);
        customer2.getMemberships().add(membership2);

        // Create membership benefits
        MembershipBenefit membershipBenefit1 = new MembershipBenefit(1L, membership1, benefit1);
        MembershipBenefit membershipBenefit2 = new MembershipBenefit(2L, membership2, benefit2);

        // Link membership benefits
        membership1.getMembershipBenefits().add(membershipBenefit1);
        membership2.getMembershipBenefits().add(membershipBenefit2);

        // Link benefits to membership benefits
        benefit1.getMembershipBenefits().add(membershipBenefit1);
        benefit2.getMembershipBenefits().add(membershipBenefit2);

        // Add customers to set
        customers.add(customer1);
        customers.add(customer2);
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customers.stream().filter(customer -> customer.getId().equals(customerId)).findFirst();
    }
    public Set<Benefit> getBenefitsForCustomer(Long customerId) {
        Optional<Customer> customerOpt = getCustomerById(customerId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.isMembership()) {
                return customer.getMemberships().stream()
                        .flatMap(membership -> membership.getMembershipBenefits().stream())
                        .map(MembershipBenefit::getBenefit)
                        .collect(Collectors.toSet());
            }
        }
        return new HashSet<>();
    }
}

