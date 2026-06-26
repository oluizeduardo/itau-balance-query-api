package com.itau.challenge.balanceapi.adapters.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Database table entity mapping.
 * <p>
 * This class is a JPA entity that maps the "account_balances" table in PostgreSQL.
 */
@Entity
@Table(name = "account_balances")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceEntity {

    @Id
    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "last_updated_timestamp", nullable = false)
    private long lastUpdatedTimestamp;
}