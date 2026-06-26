package com.itau.challenge.balanceapi.ports.in;

import com.itau.challenge.balanceapi.domain.model.Transaction;

import java.util.List;

/**
 * Input port interface that defines the use case for processing
 * incoming financial transactions.
 * It is invoked by external entrypoint like SQS message consumers.
 */
public interface ProcessTransactionUseCase {
    void execute(Transaction transaction);

    void executeBatch(List<Transaction> transactions);
}