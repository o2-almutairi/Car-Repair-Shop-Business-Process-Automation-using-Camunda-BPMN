package com.example.worker.handler;

import com.example.worker.model.Customer;
import com.example.worker.model.SampleDataService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Handles the retrieval of customer information.
 */
@Slf4j
@Component
@AllArgsConstructor
public class GetCustomerInformationHandler {
    SampleDataService sampleDataService;
    /**
     * Handles jobs of type 'getCustomerInformation'. Retrieves the customer's information
     * and completes the job with a variable indicating whether the customer has membership.
     *
     * @param client the Zeebe job client
     * @param job    the activated job
     */
    @JobWorker(type = "getCustomerInformation", autoComplete = false)
    public void handleGetCustomerInformation(final JobClient client, final ActivatedJob job) {
        long customerId = Long.parseLong((String) job.getVariable("customerId"));
        Optional<Customer> customer = sampleDataService.getCustomerById(customerId);
        log.info(customer.toString());
        logJob(job, null);
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerHasMembership", customer.get().isMembership());
        client.newCompleteCommand(job).variables(variables).send();
    }
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
