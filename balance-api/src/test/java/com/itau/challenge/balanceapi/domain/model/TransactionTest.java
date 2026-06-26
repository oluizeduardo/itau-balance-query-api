package com.itau.challenge.balanceapi.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionTest {

    @Test
    void shouldCreateTransactionWhenAttributesAreValid() {
        UUID accountId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("1500.00");
        long timestamp = 1719163200000000L;

        Transaction transaction = new Transaction(accountId, customerId, amount, timestamp);

        assertEquals(accountId, transaction.accountId());
        assertEquals(customerId, transaction.customerId());
        assertEquals(amount, transaction.amount());
        assertEquals(timestamp, transaction.timestamp());
    }

    @Test
    void shouldThrowExceptionWhenAccountIdIsNull() {
        BigDecimal amount = new BigDecimal("1500.00");

        assertThrows(IllegalArgumentException.class, () ->
                new Transaction(null, UUID.randomUUID(), amount, 123456L)
        );
    }
}