package com.itau.challenge.balanceapi.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Domain entity representing the current balance state of a customer account.
 * It holds the core business logic for balance, ensuring that
 * out-of-order updates are rejected based on transaction timestamps.
 * In Hexagonal Architecture, this entity is located at the center of the hexagon.
 */
@Getter
public class AccountBalance {
    private final UUID accountId;
    private final UUID customerId;
    private BigDecimal amount;
    private long lastUpdatedTimestamp;

    public AccountBalance(UUID accountId, UUID customerId, BigDecimal amount, long lastUpdatedTimestamp) {
        if (accountId == null || customerId == null || amount == null) {
            throw new IllegalArgumentException("Account balance fields cannot be null");
        }
        this.accountId = accountId;
        this.customerId = customerId;
        this.amount = amount;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    /**
     * Business Rule: Updates the balance only if the incoming transaction's timestamp
     * is newer (greater) than the last recorded timestamp.
     *
     * @param transaction The incoming transaction to update the balance from.
     * @return true if the balance was updated, false if the update was ignored due to out-of-order messaging.
     */
    public boolean updateBalance(Transaction transaction) {
        if (transaction.timestamp() > this.lastUpdatedTimestamp) {
            this.amount = transaction.amount();
            this.lastUpdatedTimestamp = transaction.timestamp();
            return true;
        }
        return false;
    }

}