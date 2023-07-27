package com.github.dishavarshney.Trimmy.config;

import com.github.dishavarshney.trimmy.config.RedissonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class RedissonConfigurationTest {

    private RedissonConfiguration redissonConfiguration;

    @BeforeEach
    public void setUp() {
        redissonConfiguration = new RedissonConfiguration();
    }

    @Test
    public void testCacheManager() {
        RedissonClient mockRedissonClient = mock(RedissonClient.class);
        CacheManager cacheManager = redissonConfiguration.cacheManager(mockRedissonClient);
        assertNotNull(cacheManager);
        assertTrue(cacheManager instanceof RedissonSpringCacheManager);
    }
}
