package com.itau.challenge.balanceapi.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a financial transaction processed by the core banking system.
 * This class acts as an immutable value object within the domain layer of the
 * Hexagonal Architecture, isolating transaction data from external messaging formats.
 *
 * @param accountId  The unique identifier of the bank account.
 * @param customerId The unique identifier of the account owner.
 * @param amount     The absolute updated balance carried by this transaction.
 * @param timestamp  The transaction event timestamp in microseconds.
 */
public record Transaction(
        UUID accountId,
        UUID customerId,
        BigDecimal amount,
        long timestamp
) {
    public Transaction {
        if (accountId == null || customerId == null || amount == null) {
            throw new IllegalArgumentException("Transaction attributes cannot be null");
        }
        if (timestamp <= 0) {
            throw new IllegalArgumentException("Timestamp must be a positive value in microseconds");
        }
    }
}