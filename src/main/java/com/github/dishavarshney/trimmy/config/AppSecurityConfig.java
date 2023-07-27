package com.github.dishavarshney.trimmy.config;

import com.github.dishavarshney.trimmy.service.impls.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Disha Varshney
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginServiceImpl loginService;

    public AppSecurityConfig() {
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**", "/favicon.ico");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService)
                .passwordEncoder(passwordEncoder());
    }

    @Order(1)
    @Configuration
    public static class RestConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        APIKeyAuthFilter filter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**")
                    .cors().and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/denied").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(filter)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Order(2)
    @Configuration
    public static class WebConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**")
                    .csrf()
                    .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**/favicon.ico", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.xlsx", "/fonts/**").permitAll()
                    .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs").permitAll()
                    .antMatchers("/app/oauthlogin", "/app/login", "/app/logout", "/app/register", "/*").permitAll()
                    .antMatchers("/app/home").authenticated()
                    .antMatchers("/app/home/**").authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/app/login").failureUrl("/app/login?error=loginError")
                    .successForwardUrl("/app/home")
                    .permitAll()
                    .defaultSuccessUrl("/app/home", true)
                    .and()
                    .logout().logoutUrl("/app/logout").logoutSuccessUrl("/app/login?logout=true");
        }

    }

}
