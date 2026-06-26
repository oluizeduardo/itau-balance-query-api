package com.itau.challenge.balanceapi.domain.service;

import com.itau.challenge.balanceapi.domain.exception.AccountNotFoundException;
import com.itau.challenge.balanceapi.domain.model.AccountBalance;
import com.itau.challenge.balanceapi.ports.in.GetBalanceUseCase;
import com.itau.challenge.balanceapi.ports.out.AccountBalancePort;

import java.util.UUID;

/**
 * Business service for balance inquiry.
 * <p>
 * This class orchestrates the balance inquiry use case. It receives the
 * call from the input port, uses the output port to retrieve information from
 * the database, and applies the error rule if the record does not exist.
 */
public class GetBalanceService implements GetBalanceUseCase {

    private final AccountBalancePort accountBalancePort;

    public GetBalanceService(AccountBalancePort accountBalancePort) {
        this.accountBalancePort = accountBalancePort;
    }

    @Override
    public AccountBalance execute(UUID accountId) {
        return accountBalancePort.findByAccountId(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}