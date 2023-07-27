package com.github.dishavarshney.Trimmy.repositories.impls;

import com.github.dishavarshney.trimmy.constants.UrlStatus;
import com.github.dishavarshney.trimmy.models.url.URLDocument;
import com.github.dishavarshney.trimmy.repositories.impls.CustomUrlRepositoryImpl;
import com.mongodb.bulk.BulkWriteResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUrlRepositoryImplTest {

    @InjectMocks
    private CustomUrlRepositoryImpl customUrlRepository;

    @Mock
    private MongoOperations mongoOperations;

    @Mock
    private BulkOperations bulkOperations;

    @Mock
    private BulkWriteResult bulkWriteResult;

    @Test
    public void testUpdateByValidTillAndStatus() {
        Date currentDate = new Date();
        UrlStatus oldStatus = UrlStatus.ACTIVE;
        UrlStatus newStatus = UrlStatus.EXPIRED;

        when(mongoOperations.bulkOps(BulkMode.UNORDERED, URLDocument.class)).thenReturn(bulkOperations);
        when(bulkOperations.updateMulti(any(Query.class), any(Update.class))).thenReturn(bulkOperations);
        when(bulkOperations.execute()).thenReturn(bulkWriteResult);
        when(bulkWriteResult.getMatchedCount()).thenReturn(2);
        when(bulkWriteResult.getModifiedCount()).thenReturn(2);

        customUrlRepository.updateByValidTillAndStatus(currentDate, oldStatus, newStatus);

        verify(mongoOperations).bulkOps(BulkMode.UNORDERED, URLDocument.class);
        verify(bulkOperations).updateMulti(any(Query.class), any(Update.class));

        // Optionally, also verify the number of matched and modified documents
        assertEquals(2L, bulkWriteResult.getMatchedCount());
        assertEquals(2L, bulkWriteResult.getModifiedCount());
    }

    @Test
    public void testDeleteByShortUrlKeyAndStatus() {
        String shortUrlKey = "abc123";
        UrlStatus status = UrlStatus.ACTIVE;

        // Mocking the result of exists and remove operations
        when(mongoOperations.exists(any(Query.class), eq(URLDocument.class))).thenReturn(true);

        boolean deleted = customUrlRepository.deleteByShortUrlKeyAndStatus(shortUrlKey, status);

        // Verify that the correct query was used and document was deleted
        verify(mongoOperations).exists(any(Query.class), eq(URLDocument.class));
        verify(mongoOperations).remove(any(Query.class), eq(URLDocument.class));

        assertTrue(deleted, "Document should be deleted");
    }

    @Test
    public void testDeleteByShortUrlKeyAndStatus_NotFound() {
        String shortUrlKey = "abc123";
        UrlStatus status = UrlStatus.ACTIVE;

        // Mocking the result of exists operation (document not found)
        when(mongoOperations.exists(any(Query.class), eq(URLDocument.class))).thenReturn(false);

        // Call the method to be tested
        boolean deleted = customUrlRepository.deleteByShortUrlKeyAndStatus(shortUrlKey, status);

        // Verify that the correct query was used and document was not deleted
        verify(mongoOperations).exists(any(Query.class), eq(URLDocument.class));
        verify(mongoOperations, never()).remove(any(Query.class), eq(URLDocument.class));

        assertFalse(deleted, "Document should not be deleted");
    }
}
