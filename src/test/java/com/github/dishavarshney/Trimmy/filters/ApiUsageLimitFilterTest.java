package com.github.dishavarshney.Trimmy.filters;

import com.github.dishavarshney.trimmy.filters.ApiUsageLimitFilter;
import com.github.dishavarshney.trimmy.service.interfaces.ApiUsageMonitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class ApiUsageLimitFilterTest {

    @Mock
    private ApiUsageMonitorService apiUsageMonitorService;

    @InjectMocks
    private ApiUsageLimitFilter apiUsageLimitFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoFilter_allowedRequest() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        String ipHeader = "192.168.0.1";
        when(request.getHeader("X-Real-IP")).thenReturn(ipHeader);
        when(request.getMethod()).thenReturn(HttpMethod.GET.name());
        when(apiUsageMonitorService.isAllowed(request)).thenReturn(true);

        apiUsageLimitFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    public void testDoFilter_reachedLimit() {
    }

    @Test
    public void testDoFilter_exceptionHandling() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getMethod()).thenReturn(HttpMethod.GET.name());
        when(apiUsageMonitorService.isAllowed(request)).thenThrow(new RuntimeException("Some error occurred"));

        apiUsageLimitFilter.doFilter(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
