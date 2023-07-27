package in.turls.lib.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RequestResponseLoggingFilter implements Filter {
	
	private static final Logger LOG = LogManager.getLogger(RequestResponseLoggingFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = HttpServletRequest.class.cast(request);
		LOG.info("Logging Incoming Request at {} from {}", httpRequest.getRequestURI(), httpRequest.getHeader("X-Real-IP"));
		chain.doFilter(request, response);
	}

}
