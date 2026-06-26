package com.itau.challenge.balanceapi.ports.in;

import com.itau.challenge.balanceapi.domain.model.AccountBalance;
import java.util.UUID;

/**
 * Input port interface that defines the use case for retrieving
 * the current balance of an account. It is invoked by REST controllers.
 */
public interface GetBalanceUseCase {
    AccountBalance execute(UUID accountId);
}