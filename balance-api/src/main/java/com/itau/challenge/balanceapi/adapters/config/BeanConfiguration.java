package com.itau.challenge.balanceapi.adapters.config;

import com.itau.challenge.balanceapi.domain.service.GetBalanceService;
import com.itau.challenge.balanceapi.domain.service.ProcessTransactionService;
import com.itau.challenge.balanceapi.ports.in.GetBalanceUseCase;
import com.itau.challenge.balanceapi.ports.in.ProcessTransactionUseCase;
import com.itau.challenge.balanceapi.ports.out.AccountBalancePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Spring dependency injection.
 * <p>
 * Since the domain services (GetBalanceService and ProcessTransactionService)
 * do not use Spring annotations, this class explicitly tells the framework how
 * to instantiate them and inject the necessary ports in a clean, manual way.
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public ProcessTransactionUseCase processTransactionUseCase(AccountBalancePort accountBalancePort) {
        return new ProcessTransactionService(accountBalancePort);
    }

    @Bean
    public GetBalanceUseCase getBalanceUseCase(AccountBalancePort accountBalancePort) {
        return new GetBalanceService(accountBalancePort);
    }
}