package com.example.worker.handler;

import com.example.worker.model.Benefit;
import com.example.worker.model.Customer;
import com.example.worker.model.SampleDataService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
/**
 * Handles the retrieval of membership benefits for a customer.
 */
@Slf4j
@Component
@AllArgsConstructor
public class GetCustomerBenefitHandler {
    SampleDataService sampleDataService;
    /**
     * Handles jobs of type 'getMemberShipBenefit'. Retrieves the customer's membership benefits
     * and completes the job with the benefits included in the job variables.
     *
     * @param client the Zeebe job client
     * @param job    the activated job
     */
    @JobWorker(type = "getMemberShipBenefit", autoComplete = false)
    public void handleGetMemberShipBenefit(final JobClient client, final ActivatedJob job) {
        long customerId = Long.parseLong((String) job.getVariable("customerId"));
        Set<Benefit> customerBenefit = sampleDataService.getBenefitsForCustomer(customerId);
        log.info(customerBenefit.toString());
        logJob(job, null);
        double discountRate = customerBenefit.stream().mapToDouble(Benefit::getDiscountRate).sum();
        Map<String, Object> variables = new HashMap<>();
        variables.put("discountRate", discountRate);
        client.newCompleteCommand(job).variables(variables).send();
    }
    /**
     * Logs detailed information about the job.
     *
     * @param job             the activated job
     * @param parameterValue  additional parameter value to log
     */
    private static void logJob(final ActivatedJob job, Object parameterValue) {
        log.info(
                "complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variable parameter: {}\n[variables: {}]",
                job.getType(),
                job.getKey(),
                job.getElementId(),
                job.getProcessInstanceKey(),
                Instant.ofEpochMilli(job.getDeadline()),
                job.getCustomHeaders(),
                parameterValue,
                job.getVariables());
    }
}
