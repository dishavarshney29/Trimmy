package com.github.dishavarshney.trimmy.service.impls;

import com.github.dishavarshney.trimmy.service.interfaces.ApiUsageMonitorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
public class ApiUsageMonitorServiceImpl implements ApiUsageMonitorService {

	private static final String API_USAGE_MAP_NAME = "api-usage";
	private static final Integer API_USAGE_LIMIT = 10;
	private static final Integer TTL = 60;
	private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;

	private static final Logger LOG = LogManager.getLogger(ApiUsageMonitorServiceImpl.class);

	@Autowired
	private RedissonClient redissonClient;

	@Override
	public Boolean isAllowed(final HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Real-IP");
		LOG.info("Checking API usage for IP: {}", ipAddress);
		RMapCache<String, Integer> apiUsageMapCache = redissonClient.getMapCache(API_USAGE_MAP_NAME);
		if (apiUsageMapCache.containsKey(ipAddress)) {
			Integer currentValue = apiUsageMapCache.get(ipAddress);			
			LOG.debug("Current API Usage for IP {} is {}", ipAddress, currentValue);
			if (currentValue.equals(API_USAGE_LIMIT)) {
				LOG.warn("API usage limit for the IP {} reached", ipAddress);
				return Boolean.FALSE;
			}
			apiUsageMapCache.replace(ipAddress, currentValue+1);
		} else {
			LOG.info("Creating new entry with IP {}", ipAddress);
			apiUsageMapCache.put(ipAddress, 1, TTL, TIME_UNIT);
		}
		return Boolean.TRUE;
	}

	@Override
	public Long remainingTTL(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Real-IP");
		LOG.info("Getting remaining TTL for IP {}", ipAddress);
		long remainingTTL = redissonClient.getMapCache(API_USAGE_MAP_NAME).remainTimeToLive(ipAddress);
		LOG.info("Remaining time for IP {}: {}", ipAddress, remainingTTL);
		return Long.valueOf(remainingTTL);
	}
}
