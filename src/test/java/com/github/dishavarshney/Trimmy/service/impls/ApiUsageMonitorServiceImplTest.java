//package com.github.dishavarshney.Trimmy.service.impls;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.github.dishavarshney.trimmy.service.impls.ApiUsageMonitorServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.redisson.api.RMapCache;
//import org.redisson.api.RedissonClient;
//
//import java.util.concurrent.TimeUnit;
//
//class ApiUsageMonitorServiceImplTest {
//
//    private ApiUsageMonitorServiceImpl apiUsageMonitorService;
//    private RedissonClient redissonClient;
//    private RMapCache<String, Integer> apiUsageMapCache;
//
//    private static final String API_USAGE_MAP_NAME = "api-usage";
//    private static final Integer API_USAGE_LIMIT = 10;
//    private static final Integer TTL = 60;
//    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;
//
//
//
//    @BeforeEach
//    void setUp() {
//        redissonClient = mock(RedissonClient.class);
//        apiUsageMapCache = mock(RMapCache.class);
//        when(redissonClient.getMapCache(API_USAGE_MAP_NAME)).thenReturn(apiUsageMapCache);
//        apiUsageMonitorService = new ApiUsageMonitorServiceImpl();
//        apiUsageMonitorService.redissonClient = redissonClient;
//    }
//
//    @Test
//    void testIsAllowed_ApiUsageWithinLimit() {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        String ipAddress = "192.168.0.1";
//        when(request.getHeader("X-Real-IP")).thenReturn(ipAddress);
//        when(apiUsageMapCache.containsKey(ipAddress)).thenReturn(true);
//        when(apiUsageMapCache.get(ipAddress)).thenReturn(ApiUsageMonitorServiceImpl.API_USAGE_LIMIT - 1);
//
//        boolean result = apiUsageMonitorService.isAllowed(request);
//
//        // Verify that the API usage was incremented and it returns true
//        verify(apiUsageMapCache).replace(ipAddress, API_USAGE_LIMIT - 1 + 1);
//        assert result;
//    }
//
//    @Test
//    void testIsAllowed_ApiUsageLimitReached() {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        String ipAddress = "192.168.0.2";
//        when(request.getHeader("X-Real-IP")).thenReturn(ipAddress);
//        when(apiUsageMapCache.containsKey(ipAddress)).thenReturn(true);
//        when(apiUsageMapCache.get(ipAddress)).thenReturn(API_USAGE_LIMIT);
//
//        boolean result = apiUsageMonitorService.isAllowed(request);
//
//        // Verify that the API usage was not incremented and it returns false
//        verify(apiUsageMapCache).get(ipAddress);
//        assert !result;
//    }
//
//    @Test
//    void testIsAllowed_NewIpAddress() {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        String ipAddress = "192.168.0.3";
//        when(request.getHeader("X-Real-IP")).thenReturn(ipAddress);
//        when(apiUsageMapCache.containsKey(ipAddress)).thenReturn(false);
//
//        boolean result = apiUsageMonitorService.isAllowed(request);
//
//        // Verify that a new entry was created in the cache and it returns true
//        verify(apiUsageMapCache).put(ipAddress, 1, TTL, TIME_UNIT);
//        assert result;
//    }
//
//    @Test
//    void testRemainingTTL() {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        String ipAddress = "192.168.0.4";
//        when(request.getHeader("X-Real-IP")).thenReturn(ipAddress);
//        long ttl = 60000L; // TTL in milliseconds
//        when(apiUsageMapCache.remainTimeToLive(ipAddress)).thenReturn(ttl);
//
//        long result = apiUsageMonitorService.remainingTTL(request);
//
//        // Verify that the correct remaining TTL is returned
//        verify(apiUsageMapCache).remainTimeToLive(ipAddress);
//        assert result == ttl;
//    }
//}
