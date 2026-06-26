package com.itau.challenge.balanceapi.adapters.in.consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data contract for the SQS queue payload.
 * This class is a simple DTO (Data Transfer Object) that maps the JSON structure
 * received from the AWS SQS queue into Java.
 */
public record SqsTransactionMessage(
        @JsonProperty("transaction") TransactionData transaction,
        @JsonProperty("account") AccountData account
) {
    public record TransactionData(
            UUID id,
            String type,
            BigDecimal amount,
            String currency,
            String status,
            long timestamp
    ) {
    }

    public record AccountData(
            UUID id,
            UUID owner,
            @JsonProperty("created_at") String createdAt,
            String status,
            BalanceData balance
    ) {
    }

    public record BalanceData(
            BigDecimal amount,
            String currency
    ) {
    }
}