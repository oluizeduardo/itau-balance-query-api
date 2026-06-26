package com.itau.challenge.balanceapi.domain.service;

import com.itau.challenge.balanceapi.domain.model.AccountBalance;
import com.itau.challenge.balanceapi.domain.model.Transaction;
import com.itau.challenge.balanceapi.ports.in.ProcessTransactionUseCase;
import com.itau.challenge.balanceapi.ports.out.AccountBalancePort;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Core application service that orchestrates the business use case for updating
 * an account balance upon receiving a transaction event.
 * This class remains framework-agnostic.
 */
public class ProcessTransactionService implements ProcessTransactionUseCase {

    private final AccountBalancePort accountBalancePort;

    public ProcessTransactionService(AccountBalancePort accountBalancePort) {
        this.accountBalancePort = accountBalancePort;
    }

    @Override
    @Transactional
    public void execute(Transaction transaction) {
        processSingle(transaction);
    }

    @Override
    @Transactional
    public void executeBatch(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            processSingle(transaction);
        }
    }

    private void processSingle(Transaction transaction) {
        AccountBalance currentBalance = accountBalancePort.findByAccountId(transaction.accountId())
                .orElseGet(() -> new AccountBalance(
                        transaction.accountId(),
                        transaction.customerId(),
                        BigDecimal.ZERO,
                        0L));

        boolean wasUpdated = currentBalance.updateBalance(transaction);

        if (wasUpdated) {
            accountBalancePort.save(currentBalance);
        }
    }
}