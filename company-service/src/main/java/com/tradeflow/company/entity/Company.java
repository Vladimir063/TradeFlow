package com.tradeflow.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash("Company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    private String companyId;

    @Indexed
    private String name;

    private Integer ask;

    private Integer bid;
}
