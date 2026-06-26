package com.itau.challenge.balanceapi.adapters.in.web;

import com.itau.challenge.balanceapi.adapters.in.web.dto.BalanceResponse;
import com.itau.challenge.balanceapi.domain.model.AccountBalance;
import com.itau.challenge.balanceapi.ports.in.GetBalanceUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST API Controller for balance queries.
 * <p>
 * It exposes the REST endpoint for balance queries, receives the client request,
 * invokes the corresponding entry port (GetBalanceUseCase), and returns the properly
 * formatted DTO.
 */
@Slf4j
@RestController
@RequestMapping("/balances")
public class BalanceController {

    private final GetBalanceUseCase getBalanceUseCase;

    public BalanceController(GetBalanceUseCase getBalanceUseCase) {
        this.getBalanceUseCase = getBalanceUseCase;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID accountId) {
        log.info("Balance requested for the account ID: [{}]", accountId);
        AccountBalance accountBalance = getBalanceUseCase.execute(accountId);
        return ResponseEntity.ok(BalanceResponse.fromDomain(accountBalance));
    }
}