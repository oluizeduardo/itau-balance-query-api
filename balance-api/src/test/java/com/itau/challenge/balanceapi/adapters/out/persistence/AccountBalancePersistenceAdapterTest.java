package com.itau.challenge.balanceapi.adapters.out.persistence;

import com.itau.challenge.balanceapi.adapters.out.persistence.entity.AccountBalanceEntity;
import com.itau.challenge.balanceapi.adapters.out.persistence.repository.SpringDataBalanceRepository;
import com.itau.challenge.balanceapi.domain.model.AccountBalance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountBalancePersistenceAdapterTest {

    @Mock
    private SpringDataBalanceRepository repository;

    @InjectMocks
    private AccountBalancePersistenceAdapter adapter;

    @Test
    void shouldFindAccountBalanceAndConvertToDomain() {
        UUID accountId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        AccountBalanceEntity entity = new AccountBalanceEntity(accountId, customerId, BigDecimal.TEN, 200L);

        when(repository.findById(accountId)).thenReturn(Optional.of(entity));

        Optional<AccountBalance> result = adapter.findByAccountId(accountId);

        assertTrue(result.isPresent());
        assertEquals(accountId, result.get().getAccountId());
        assertEquals(BigDecimal.TEN, result.get().getAmount());
        assertEquals(200L, result.get().getLastUpdatedTimestamp());
    }

    @Test
    void shouldSaveDomainObjectAsDatabaseEntity() {
        UUID accountId = UUID.randomUUID();
        AccountBalance domainModel = new AccountBalance(accountId, UUID.randomUUID(), BigDecimal.ONE, 500L);

        adapter.save(domainModel);

        verify(repository, times(1)).save(any(AccountBalanceEntity.class));
    }
}