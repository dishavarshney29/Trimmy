package com.github.dishavarshney.Trimmy.config;

import com.github.dishavarshney.trimmy.config.FilterConfiguration;
import com.github.dishavarshney.trimmy.filters.ApiUsageLimitFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.servlet.Filter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class FilterConfigurationTest {

    private FilterConfiguration filterConfiguration;

    @BeforeEach
    public void setUp() {
        filterConfiguration = new FilterConfiguration();
    }

    @Test
    public void testApiUsageLimitFilterRegistrationBean() {
        ApiUsageLimitFilter mockApiUsageLimitFilter = mock(ApiUsageLimitFilter.class);

        FilterRegistrationBean<Filter> registrationBean = filterConfiguration.apiUsageLimitFilterRegistrationBean(mockApiUsageLimitFilter);

        assertNotNull(registrationBean);
        assertSame(mockApiUsageLimitFilter, registrationBean.getFilter());
        assertEquals("/v1/url/*", registrationBean.getUrlPatterns().iterator().next());
        assertEquals(2, registrationBean.getOrder());
    }

    @Test
    public void testApiUsageLimitFilter() {
        Filter apiUsageLimitFilter = filterConfiguration.apiUsageLimitFilter();
        assertNotNull(apiUsageLimitFilter);
        assertEquals(ApiUsageLimitFilter.class, apiUsageLimitFilter.getClass());
    }
}
