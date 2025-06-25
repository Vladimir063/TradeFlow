package com.tradeflow.funds.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.tradeflow.funds.repository.jpa")
@EnableRedisRepositories(basePackages = "com.tradeflow.funds.repository.redis")
public class PersistenceConfig {
}