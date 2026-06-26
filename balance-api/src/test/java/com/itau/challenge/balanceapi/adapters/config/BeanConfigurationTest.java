package com.itau.challenge.balanceapi.adapters.config;

import com.itau.challenge.balanceapi.ports.in.GetBalanceUseCase;
import com.itau.challenge.balanceapi.ports.in.ProcessTransactionUseCase;
import com.itau.challenge.balanceapi.ports.out.AccountBalancePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    @Mock
    private AccountBalancePort accountBalancePort;

    private final BeanConfiguration configuration = new BeanConfiguration();

    @Test
    void shouldCreateProcessTransactionUseCaseBean() {
        ProcessTransactionUseCase useCase = configuration.processTransactionUseCase(accountBalancePort);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateGetBalanceUseCaseBean() {
        GetBalanceUseCase useCase = configuration.getBalanceUseCase(accountBalancePort);
        assertNotNull(useCase);
    }
}