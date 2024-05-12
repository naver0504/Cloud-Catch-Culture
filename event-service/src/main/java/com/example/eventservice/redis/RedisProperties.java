package com.example.eventservice.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
public record RedisProperties(String host, int port) {
}
