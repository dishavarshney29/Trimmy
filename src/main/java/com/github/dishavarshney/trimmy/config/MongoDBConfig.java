package com.github.dishavarshney.trimmy.config;

import com.github.dishavarshney.trimmy.entity.PrePersistListener;
import com.github.dishavarshney.trimmy.entity.PreUpdateListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;

@Configuration
@EnableMongoAuditing
public class MongoDBConfig {

    @Bean
    public AbstractMongoEventListener<PrePersistListener> prePersistListener() {
        return new AbstractMongoEventListener<PrePersistListener>() {
            @Override
            public void onBeforeConvert(BeforeConvertEvent<PrePersistListener> event) {
                event.getSource().onPrePersist();
            }
        };
    }

    @Bean
    public AbstractMongoEventListener<PreUpdateListener> preUpdateListener() {
        return new AbstractMongoEventListener<PreUpdateListener>() {
            @Override
            public void onBeforeSave(BeforeSaveEvent<PreUpdateListener> event) {
                event.getSource().onPreUpdate();
            }
        };
    }
}
