package com.github.dishavarshney.trimmy;

import com.github.dishavarshney.trimmy.controller.service.URLService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
@EnableSwagger2
public class TrimmyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TrimmyApplication.class, args);

        URLService urlService = context.getBean(URLService.class);
        urlService.scheduleDeleteExpiredURLsTask();
    }

}
