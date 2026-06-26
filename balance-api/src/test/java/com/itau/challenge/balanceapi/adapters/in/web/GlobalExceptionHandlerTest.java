package com.itau.challenge.balanceapi.adapters.in.web;

import com.itau.challenge.balanceapi.adapters.in.web.dto.ApiErrorResponse;
import com.itau.challenge.balanceapi.domain.exception.AccountNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Mock
    private HttpServletRequest request;

    @Test
    void shouldReturnNotFoundWhenAccountNotFoundExceptionIsThrown() {
        var accountId = UUID.randomUUID();
        var requestedURI = "/balances/"+accountId;
        AccountNotFoundException exception = new AccountNotFoundException(accountId);
        when(request.getRequestURI()).thenReturn(requestedURI);

        ResponseEntity<ApiErrorResponse> response = handler.handleAccountNotFound(exception, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().status());
        assertEquals("Not Found", response.getBody().error());
        assertEquals("Account not found", response.getBody().message());
        assertEquals(requestedURI, response.getBody().path());
    }

    @Test
    void shouldReturnBadRequestWhenIllegalArgumentExceptionIsThrown() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid data");
        when(request.getRequestURI()).thenReturn("/balances/abc");

        ResponseEntity<ApiErrorResponse> response = handler.handleIllegalArgument(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Bad Request", response.getBody().error());
        assertEquals("Invalid data", response.getBody().message());
    }
}