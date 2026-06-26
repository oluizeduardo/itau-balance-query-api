package com.itau.challenge.balanceapi.adapters.in.web;

import com.itau.challenge.balanceapi.adapters.in.web.dto.BalanceResponse;
import com.itau.challenge.balanceapi.domain.model.AccountBalance;
import com.itau.challenge.balanceapi.ports.in.GetBalanceUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceControllerTest {

    @Mock
    private GetBalanceUseCase getBalanceUseCase;

    @InjectMocks
    private BalanceController controller;

    @Test
    void shouldReturnBalanceResponseWhenAccountExists() {
        UUID accountId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        // Timestamp simulating 2026-06-23T19:52:00.000-03:00 in microseconds (1782255120000000L)
        long timestampMicros = 1782255120000000L;

        AccountBalance domainBalance = new AccountBalance(accountId, customerId, new BigDecimal("1250.75"), timestampMicros);

        when(getBalanceUseCase.execute(accountId)).thenReturn(domainBalance);

        ResponseEntity<BalanceResponse> responseEntity = controller.getBalance(accountId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        BalanceResponse body = responseEntity.getBody();
        assertEquals(accountId, body.id());
        assertEquals(customerId, body.owner());
        assertEquals(new BigDecimal("1250.75"), body.balance().amount());
        assertEquals("2026-06-23T19:52:00.000-03:00", body.updatedAt());

        verify(getBalanceUseCase, times(1)).execute(accountId);
    }
}