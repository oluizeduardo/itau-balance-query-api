package com.itau.challenge.balanceapi.domain.service;

import com.itau.challenge.balanceapi.domain.model.AccountBalance;
import com.itau.challenge.balanceapi.domain.model.Transaction;
import com.itau.challenge.balanceapi.ports.out.AccountBalancePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessTransactionServiceTest {

    @Mock
    private AccountBalancePort accountBalancePort;

    @InjectMocks
    private ProcessTransactionService service;

    @Test
    void shouldSaveBalanceWhenTransactionIsNewer() {
        UUID accountId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Transaction transaction = new Transaction(accountId, customerId, BigDecimal.TEN, 500L);
        AccountBalance existingBalance = new AccountBalance(accountId, transaction.customerId(), BigDecimal.ZERO, 100L);

        when(accountBalancePort.findByAccountId(accountId)).thenReturn(Optional.of(existingBalance));

        service.execute(transaction);

        verify(accountBalancePort, times(1)).save(existingBalance);
    }

    @Test
    void shouldNotSaveBalanceWhenTransactionIsOutdated() {
        UUID accountId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Transaction transaction = new Transaction(accountId, customerId, BigDecimal.TEN, 50L);
        AccountBalance existingBalance = new AccountBalance(accountId, transaction.customerId(), BigDecimal.ZERO, 100L);

        when(accountBalancePort.findByAccountId(accountId)).thenReturn(Optional.of(existingBalance));

        service.execute(transaction);

        verify(accountBalancePort, never()).save(any());
    }
}