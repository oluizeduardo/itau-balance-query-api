package com.itau.challenge.balanceapi.adapters.in.consumer;

import com.itau.challenge.balanceapi.adapters.in.consumer.dto.SqsTransactionMessage;
import com.itau.challenge.balanceapi.domain.model.Transaction;
import com.itau.challenge.balanceapi.ports.in.ProcessTransactionUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AWS SQS Message consumer.
 * This class is an Incoming Adapter. It listens to the SQS queue,
 * receives the transaction messages, converts them into a pure domain object,
 * and triggers the business logic through the ProcessTransactionUseCase port.
 */
@Slf4j
@Component
public class TransactionSqsListener {

    private final ProcessTransactionUseCase processTransactionUseCase;

    public TransactionSqsListener(ProcessTransactionUseCase processTransactionUseCase) {
        this.processTransactionUseCase = processTransactionUseCase;
    }

    @SqsListener(value = "${cloud.aws.sqs.queue-name}")
    public void listen(List<SqsTransactionMessage> messages) {
        if (messages == null || messages.isEmpty()) {
            log.warn("Null or empty list of messages received by the SQS Listener.");
            return;
        }

        log.info("Batch received from SQS containing {} messages.", messages.size());

        // Converts the list of DTOs from the queue into a list of Domain objects.
        List<Transaction> domainTransactions = messages.stream()
                .filter(msg -> msg != null && msg.account() != null && msg.transaction() != null)
                .map(msg -> new Transaction(
                        msg.account().id(),
                        msg.account().owner(),
                        msg.account().balance().amount(),
                        msg.transaction().timestamp()
                ))
                .toList();

        try {
            processTransactionUseCase.executeBatch(domainTransactions);

            log.debug("Batch of {} transactions successfully processed.", domainTransactions.size());
        } catch (Exception e) {
            log.error("Error processing SQS message batch", e);
            throw e;
        }
    }
}