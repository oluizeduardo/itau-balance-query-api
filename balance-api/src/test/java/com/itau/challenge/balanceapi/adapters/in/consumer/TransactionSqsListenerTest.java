package com.itau.challenge.balanceapi.adapters.in.consumer;

import com.itau.challenge.balanceapi.adapters.in.consumer.dto.SqsTransactionMessage;
import com.itau.challenge.balanceapi.domain.model.Transaction;
import com.itau.challenge.balanceapi.ports.in.ProcessTransactionUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionSqsListenerTest {

    @Mock
    private ProcessTransactionUseCase useCase;

    @InjectMocks
    private TransactionSqsListener listener;

    @Test
    void shouldParseMessageAndCallUseCaseSuccessfully() {
        UUID accountId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();

        var balanceData = new SqsTransactionMessage.BalanceData(new BigDecimal("250.00"), "BRL");
        var accountData = new SqsTransactionMessage.AccountData(accountId, customerId, "25/06/2026", "APPROVED", balanceData);
        var transactionData = new SqsTransactionMessage.TransactionData(transactionId, "CREDIT", new BigDecimal("250.00"),
                "BRL", "APPROVED", 1719163200000L);

        var message = new SqsTransactionMessage(transactionData, accountData);

        List<SqsTransactionMessage> messageBatch = List.of(message);

        listener.listen(messageBatch);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Transaction>> captor = ArgumentCaptor.forClass(List.class);
        verify(useCase, times(1)).executeBatch(captor.capture());

        List<Transaction> capturedList = captor.getValue();
        assertEquals(1, capturedList.size());

        Transaction captured = capturedList.getFirst();
        assertEquals(accountId, captured.accountId());
        assertEquals(customerId, captured.customerId());
        assertEquals(new BigDecimal("250.00"), captured.amount());
        assertEquals(1719163200000L, captured.timestamp());
    }

    @Test
    void shouldIgnoreNullMessage() {
        listener.listen(null);
        verifyNoInteractions(useCase);
    }
}