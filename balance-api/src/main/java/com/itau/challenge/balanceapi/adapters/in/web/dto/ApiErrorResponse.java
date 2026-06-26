package com.itau.challenge.balanceapi.adapters.in.web.dto;

import java.time.Instant;

/**
 * Standard model for API error responses.
 */
public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {}