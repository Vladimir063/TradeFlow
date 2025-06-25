package com.tradeflow.execution.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.tradeflow.execution.repository.jpa")
@EnableRedisRepositories(basePackages = "com.tradeflow.execution.repository.redis")
public class PersistenceConfig {
}