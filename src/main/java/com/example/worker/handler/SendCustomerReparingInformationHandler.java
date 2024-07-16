package com.example.worker.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Handles sending repairing information to a customer.
 */
@Slf4j
@Component
@AllArgsConstructor
public class SendCustomerReparingInformationHandler {
    /**
     * Handles jobs of type 'sendCustomerRepairingInformation'. Logs the customer ID and the action of sending
     * repairing information to the customer, then completes the job.
     *
     * @param client the Zeebe job client
     * @param job    the activated job
     */
    @JobWorker(type = "sendCustomerReparingInformation", autoComplete = false)
    public void handleSendCustomerReparingInformation(final JobClient client, final ActivatedJob job) {
        long customerId = Long.parseLong((String) job.getVariable("customerId"));
        log.info(String.valueOf(customerId));
        logJob(job, null);
        log.info("Sending customer repairing information to customer: customerId");
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
