package com.github.dishavarshney.Trimmy.config;

import com.github.dishavarshney.trimmy.config.MongoDBConfig;
import com.github.dishavarshney.trimmy.models.PrePersistListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MongoDBConfigTest {

    private MongoDBConfig mongoDBConfig;

    @BeforeEach
    public void setUp() {
        mongoDBConfig = new MongoDBConfig();
    }

    @Test
    public void testPrePersistListener() {
        PrePersistListener prePersistListener = mock(PrePersistListener.class);
        AbstractMongoEventListener<PrePersistListener> listener = mongoDBConfig.prePersistListener();

        BeforeConvertEvent<PrePersistListener> event = new BeforeConvertEvent<>(prePersistListener, "urls");
        listener.onBeforeConvert(event);

        verify(prePersistListener).onPrePersist();
    }
}
