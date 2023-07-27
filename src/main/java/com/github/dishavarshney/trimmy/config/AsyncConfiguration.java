package com.github.dishavarshney.trimmy.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

	private static final Logger LOG = LogManager.getLogger(AsyncConfiguration.class);

	@Override
	@Bean("asyncExecutor")
	public Executor getAsyncExecutor() {
		LOG.info("Configuring ASYNC Executor");

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(100);
		executor.setMaxPoolSize(500);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("AsyncThread-");
		executor.initialize();

		LOG.info("ASYNC Executor Configuration Complete");

		return executor;
	}

}
