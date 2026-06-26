package com.itau.challenge.balanceapi.ports.out;

import com.itau.challenge.balanceapi.domain.model.AccountBalance;

import java.util.Optional;
import java.util.UUID;

/**
 * Defines the contract that the persistence adapter must implement to allow
 * the domain layer to fetch and save the state of an account balance.
 */
public interface AccountBalancePort {
    Optional<AccountBalance> findByAccountId(UUID accountId);

    void save(AccountBalance accountBalance);
}