package com.itau.challenge.balanceapi.adapters.out.persistence;

import com.itau.challenge.balanceapi.adapters.out.persistence.entity.AccountBalanceEntity;
import com.itau.challenge.balanceapi.adapters.out.persistence.repository.SpringDataBalanceRepository;
import com.itau.challenge.balanceapi.domain.model.AccountBalance;
import com.itau.challenge.balanceapi.ports.out.AccountBalancePort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Database Persistence Adapter.
 * <p>
 * This class is an Outgoing Adapter. It implements the AccountBalancePort
 * interface defined by the domain core. It uses Spring Data to fetch/save data from PostgreSQL
 * and translates database entities to pure domain objects.
 */
@Component
public class AccountBalancePersistenceAdapter implements AccountBalancePort {

    private final SpringDataBalanceRepository repository;

    public AccountBalancePersistenceAdapter(SpringDataBalanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AccountBalance> findByAccountId(UUID accountId) {
        return repository.findById(accountId)
                .map(entity -> new AccountBalance(
                        entity.getAccountId(),
                        entity.getCustomerId(),
                        entity.getAmount(),
                        entity.getLastUpdatedTimestamp()
                ));
    }

    @Override
    public void save(AccountBalance accountBalance) {
        AccountBalanceEntity entity = new AccountBalanceEntity(
                accountBalance.getAccountId(),
                accountBalance.getCustomerId(),
                accountBalance.getAmount(),
                accountBalance.getLastUpdatedTimestamp()
        );
        repository.save(entity);
    }
}