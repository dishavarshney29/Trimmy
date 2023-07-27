package com.github.dishavarshney.trimmy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
@EnableSwagger2
@EnableCaching
@EnableMongoRepositories
@EnableAsync
@EnableScheduling
public class TrimmyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TrimmyApplication.class, args);
    }
}
