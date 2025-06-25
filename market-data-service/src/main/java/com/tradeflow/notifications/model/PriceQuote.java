package com.tradeflow.notifications.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash("PriceQuote")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceQuote {

    @Id
    private UUID companyId;

    private String company;

    private Integer bid;

    private Integer ask;
}
