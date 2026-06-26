package com.itau.challenge.balanceapi.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itau.challenge.balanceapi.domain.model.AccountBalance;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * HTTP REST response payload contract.
 */
public record BalanceResponse(
        UUID id,
        UUID owner,
        BalanceData balance,
        @JsonProperty("updated_at") String updatedAt
) {
    // Timezone -03:00 (America/Sao_Paulo)
    private static final DateTimeFormatter ISO_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .withZone(ZoneId.of("America/Sao_Paulo"));

    public record BalanceData(
            BigDecimal amount,
            String currency
    ) {
    }

    public static BalanceResponse fromDomain(AccountBalance accountBalance) {
        long microSeconds = accountBalance.getLastUpdatedTimestamp();
        long seconds = microSeconds / 1_000_000;
        long nanoAdjustment = (microSeconds % 1_000_000) * 1_000;

        Instant instant = Instant.ofEpochSecond(seconds, nanoAdjustment);
        String formattedDate = ISO_FORMATTER.format(instant);

        return new BalanceResponse(
                accountBalance.getAccountId(),
                accountBalance.getCustomerId(),
                new BalanceData(accountBalance.getAmount(), "BRL"),
                formattedDate
        );
    }
}