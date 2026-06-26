package com.itau.challenge.balanceapi.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountBalanceTest {

    @Test
    void shouldUpdateBalanceWhenTransactionIsNewer() {
        UUID accountId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        AccountBalance balance = new AccountBalance(accountId, customerId, BigDecimal.ZERO, 1000L);
        Transaction newerTransaction = new Transaction(accountId, customerId, BigDecimal.TEN, 1001L);

        boolean updated = balance.updateBalance(newerTransaction);

        assertTrue(updated);
        assertEquals(BigDecimal.TEN, balance.getAmount());
        assertEquals(1001L, balance.getLastUpdatedTimestamp());
    }

    @Test
    void shouldNotUpdateBalanceWhenTransactionIsOlderOrEqual() {
        UUID accountId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        AccountBalance balance = new AccountBalance(accountId, customerId, BigDecimal.TEN, 1000L);
        Transaction olderTransaction = new Transaction(accountId, customerId, BigDecimal.ZERO, 999L);

        boolean updated = balance.updateBalance(olderTransaction);

        assertFalse(updated);
        assertEquals(BigDecimal.TEN, balance.getAmount());
        assertEquals(1000L, balance.getLastUpdatedTimestamp());
    }
}