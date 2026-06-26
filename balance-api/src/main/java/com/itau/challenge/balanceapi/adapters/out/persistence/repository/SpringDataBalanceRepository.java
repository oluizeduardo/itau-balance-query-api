package com.itau.challenge.balanceapi.adapters.out.persistence.repository;

import com.itau.challenge.balanceapi.adapters.out.persistence.entity.AccountBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for database access.
 * <p>
 * Provides ready-to-use CRUD methods.
 * It is a tool used by the persistence adapter to communicate with the real database.
 */
@Repository
public interface SpringDataBalanceRepository extends JpaRepository<AccountBalanceEntity, UUID> {
}