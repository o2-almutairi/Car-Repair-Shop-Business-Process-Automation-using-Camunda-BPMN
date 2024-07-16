package com.example.worker.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
/**
 * Handles offering a two-week warranty to a customer.
 */
@Slf4j
@Component
@AllArgsConstructor
public class OfferTwoWeekWarrantyHandler {
    /**
     * Handles jobs of type 'offerTwoWeekWarranty'. Logs the customer ID and the action of offering
     * a two-week warranty to the customer, then completes the job.
     *
     * @param client the Zeebe job client
     * @param job    the activated job
     */
    @JobWorker(type = "offerTwoWeekWarranty", autoComplete = false)
    public void handleOfferTwoWeekWarranty(final JobClient client, final ActivatedJob job) {
        long customerId = Long.parseLong((String) job.getVariable("customerId"));
        log.info(String.valueOf(customerId));
        logJob(job, null);
        log.info("Offer Two Week Warranty to customer: customerId");
        client.newCompleteCommand(job).send();
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
