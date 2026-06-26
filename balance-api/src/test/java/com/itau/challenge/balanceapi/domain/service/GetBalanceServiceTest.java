package com.itau.challenge.balanceapi.domain.service;

import com.itau.challenge.balanceapi.domain.exception.AccountNotFoundException;
import com.itau.challenge.balanceapi.domain.model.AccountBalance;
import com.itau.challenge.balanceapi.ports.out.AccountBalancePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBalanceServiceTest {

    @Mock
    private AccountBalancePort accountBalancePort;

    @InjectMocks
    private GetBalanceService service;

    @Test
    void shouldReturnBalanceWhenAccountExists() {
        UUID accountId = UUID.randomUUID();
        AccountBalance mockBalance = new AccountBalance(accountId, UUID.randomUUID(), BigDecimal.TEN, 100L);

        when(accountBalancePort.findByAccountId(accountId)).thenReturn(Optional.of(mockBalance));

        AccountBalance result = service.execute(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getAccountId());
        verify(accountBalancePort, times(1)).findByAccountId(accountId);
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {
        UUID accountId = UUID.randomUUID();
        when(accountBalancePort.findByAccountId(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.execute(accountId));
        verify(accountBalancePort, times(1)).findByAccountId(accountId);
    }
}