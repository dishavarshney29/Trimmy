package com.github.dishavarshney.trimmy.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan(AppConfig.BASE_PACKAGE)
@EnableJpaRepositories(basePackages = {AppConfig.REPOSITORY_PACKAGE})
@Configuration
public class AppConfig implements WebMvcConfigurer {

    public static final String BASE_PACKAGE = "com.github.dishavarshney.trimmy";
    public static final String ENTITY_PACKAGE = "com.github.dishavarshney.trimmy.models";
    public static final String REPOSITORY_PACKAGE = "com.github.dishavarshney.trimmy.repositories";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/img/**",
                "/css/**",
                "/js/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

//    @Bean
//    public ViewResolver internalResourceViewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        bean.setViewClass(JstlView.class);
//        bean.setPrefix("WEB-INF/views/");
//        bean.setSuffix(".jsp");
//        return bean;
//    }
//
////    @Override
////    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
////        configurer.enable();
////    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//    }
}
