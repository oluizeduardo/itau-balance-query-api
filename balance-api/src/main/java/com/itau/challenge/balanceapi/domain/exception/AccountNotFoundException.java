package com.itau.challenge.balanceapi.domain.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Exception thrown when an account is not found.
 */
@Getter
public class AccountNotFoundException extends RuntimeException {
    private final UUID accountId;

    public AccountNotFoundException(UUID accountId) {
        super("Account not found");
        this.accountId = accountId;
    }
}