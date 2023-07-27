package in.turls.lib.configurations;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.turls.lib.filters.ApiUsageLimitFilter;

@Configuration
public class FilterConfiguration {
	
	@Bean
	public FilterRegistrationBean<Filter> apiUsageLimitFilterRegistrationBean(@Autowired ApiUsageLimitFilter apiUsageLimitFilter) {
		
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(apiUsageLimitFilter);
		registrationBean.addUrlPatterns("/v1/url/*");
		registrationBean.setName("apiUsageLimitFiler");
		registrationBean.setOrder(2);
		return registrationBean;
	}
	
	@Bean
	public ApiUsageLimitFilter apiUsageLimitFilter() {
		return new ApiUsageLimitFilter();
	}

}
