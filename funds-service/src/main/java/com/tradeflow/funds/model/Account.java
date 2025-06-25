package com.tradeflow.funds.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "account")
public class Account {

    @Id
    private UUID userId;

    private Integer availableMoney;

    private Integer reserved;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
