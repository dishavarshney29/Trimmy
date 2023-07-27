package com.github.dishavarshney.Trimmy.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.Executor;

import com.github.dishavarshney.trimmy.config.AsyncConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class AsyncConfigurationTest {

    private AsyncConfiguration asyncConfiguration;

    @BeforeEach
    public void setUp() {
        asyncConfiguration = new AsyncConfiguration();
    }

    @Test
    public void testAsyncExecutorConfiguration() {
        Executor executor = asyncConfiguration.getAsyncExecutor();
        assertNotNull(executor);
        assertEquals(ThreadPoolTaskExecutor.class, executor.getClass());

        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) executor;
        assertEquals(100, taskExecutor.getCorePoolSize());
        assertEquals(500, taskExecutor.getMaxPoolSize());
        assertEquals("AsyncThread-", taskExecutor.getThreadNamePrefix());
    }
}
