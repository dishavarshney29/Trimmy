package com.github.dishavarshney.trimmy.service.interfaces;

import javax.servlet.http.HttpServletRequest;

public interface ApiUsageMonitorService {
	
	Boolean isAllowed(final HttpServletRequest request);
	Long remainingTTL(final HttpServletRequest request);
}
