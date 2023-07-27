package com.github.dishavarshney.Trimmy.service.impls;

import com.github.dishavarshney.trimmy.constants.UrlStatus;
import com.github.dishavarshney.trimmy.models.url.URLDocument;
import com.github.dishavarshney.trimmy.repositories.URLRepository;
import com.github.dishavarshney.trimmy.repositories.UserRepository;
import com.github.dishavarshney.trimmy.service.impls.URLManagerServiceImpl;
import com.github.dishavarshney.trimmy.service.interfaces.CounterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class URLManagerServiceImplTest {

    @Mock
    private URLRepository urlRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CounterService counterService;

    @Mock
    private RedissonClient redissonClient;

    @InjectMocks
    private URLManagerServiceImpl urlManagerService;

//    @BeforeEach
//    public void setUp() {
//        // Add any setup required for the test cases.
//    }

//    @Test
//    public void testCreateShortUrlKeyWithGeneratedShortUrl() throws DuplicateKeyException, NoSuchElementException, InvalidCustomShortUrl, InvalidCustomShortUrl {
//        // Mock input values
//        String longUrl = "http://www.example.com";
//        String customShortUrl = null; // Pass null for auto-generated short URL
//        UrlExpiryUnit expiryUnit = UrlExpiryUnit.DAYS;
//        Integer expiryValue = 7;
//
//        // Mock the repository method to return an empty Optional, indicating that the custom short URL is available.
//        when(urlRepository.findOneByShortUrlKeyAndStatus(any(), eq(UrlStatus.ACTIVE))).thenReturn(Optional.empty());
//
//        // Mock the counterService to return a specific counter value.
//        when(counterService.getNextCounterNumber()).thenReturn(12345L);
//
//        // Call the method
//        Optional<ShortUrlInfo> shortUrlInfo = urlManagerService.createShortUrlKey(longUrl, customShortUrl, expiryUnit, expiryValue);
//
//        // Assertions
//        assertTrue(shortUrlInfo.isPresent());
//        assertNotNull(shortUrlInfo.get().getKey());
//        assertEquals("http://shorturl_domain/r/" + shortUrlInfo.get().getKey(), shortUrlInfo.get().getCompleteUrl());
//        // Add more assertions based on your implementation.
//    }

    @Test
    public void testRetrieveOriginalUrlFromCache() throws NoSuchElementException {
        // Mock input values
        String shortUrlKey = "existing_short_url_key";

        // Mock the Redis cache to contain the short URL key.
        RMapCache<String, String> urlCache = Mockito.mock(RMapCache.class);
        when(redissonClient.<String, String>getMapCache(anyString())).thenReturn(urlCache);
        when(urlCache.containsKey(shortUrlKey)).thenReturn(true);
        when(urlCache.get(shortUrlKey)).thenReturn("http://www.example.com");

        // Call the method
        Optional<String> originalUrl = urlManagerService.retrieveOriginalUrl(shortUrlKey);

        // Assertions
        assertTrue(originalUrl.isPresent());
        assertEquals("http://www.example.com", originalUrl.get());
    }

    @Test
    public void testRetrieveOriginalUrlFromDatabase() throws NoSuchElementException {
        // Mock input values
        String shortUrlKey = "existing_short_url_key";

        // Mock the Redis cache to not contain the short URL key.
        RMapCache<String, String> urlCache = Mockito.mock(RMapCache.class);
        when(redissonClient.<String, String>getMapCache(anyString())).thenReturn(urlCache);
        when(urlCache.containsKey(shortUrlKey)).thenReturn(false);

        // Mock the repository method to return an Optional with a URLDocument.
        URLDocument urlDocument = new URLDocument();
        urlDocument.setOriginalUrl("http://www.example.com");
        urlDocument.setValidTill(new Date(System.currentTimeMillis() + 1000)); // URL is not expired.
        when(urlRepository.findOneByShortUrlKeyAndStatus(shortUrlKey, UrlStatus.ACTIVE)).thenReturn(Optional.of(urlDocument));

        // Call the method
        Optional<String> originalUrl = urlManagerService.retrieveOriginalUrl(shortUrlKey);

        // Assertions
        assertTrue(originalUrl.isPresent());
        assertEquals("http://www.example.com", originalUrl.get());
    }

    @Test
    public void testRetrieveOriginalUrlExpired() throws NoSuchElementException {
        // Mock input values
        String shortUrlKey = "existing_short_url_key";

        // Mock the Redis cache to not contain the short URL key.
        RMapCache<String, String> urlCache = Mockito.mock(RMapCache.class);
        when(redissonClient.<String, String>getMapCache(anyString())).thenReturn(urlCache);
        when(urlCache.containsKey(shortUrlKey)).thenReturn(false);

        // Mock the repository method to return an Optional with an expired URLDocument.
        URLDocument urlDocument = new URLDocument();
        urlDocument.setOriginalUrl("http://www.example.com");
        urlDocument.setValidTill(new Date(System.currentTimeMillis() - 1000)); // URL is expired.
        when(urlRepository.findOneByShortUrlKeyAndStatus(shortUrlKey, UrlStatus.ACTIVE)).thenReturn(Optional.of(urlDocument));

        Optional<String> originalUrl = urlManagerService.retrieveOriginalUrl(shortUrlKey);

        assertFalse(originalUrl.isPresent());
    }

    @Test
    public void testDeleteUrlEntityExists() throws NoSuchElementException {
        String shortUrlKey = "existing_short_url_key";

        // Mock the Redis cache to contain the short URL key.
        RMapCache<String, String> urlCache = Mockito.mock(RMapCache.class);
        when(redissonClient.<String, String>getMapCache(anyString())).thenReturn(urlCache);
        when(urlCache.containsKey(shortUrlKey)).thenReturn(true);

        // Mock the repository method to return true when deleting the URL entity.
        when(urlRepository.deleteByShortUrlKeyAndStatus(shortUrlKey, UrlStatus.ACTIVE)).thenReturn(true);

        Optional<Boolean> result = urlManagerService.deleteUrlEntity(shortUrlKey);

        assertTrue(result.isPresent());
        assertTrue(result.get());
    }

    @Test
    public void testDeleteUrlEntityNotExists() throws NoSuchElementException {
        // Mock input values
        String shortUrlKey = "non_existing_short_url_key";

        // Mock the Redis cache to not contain the short URL key.
        RMapCache<String, String> urlCache = Mockito.mock(RMapCache.class);
        when(redissonClient.<String, String>getMapCache(anyString())).thenReturn(urlCache);
        when(urlCache.containsKey(shortUrlKey)).thenReturn(false);

        // Mock the repository method to return false when trying to delete a non-existing URL entity.
        when(urlRepository.deleteByShortUrlKeyAndStatus(shortUrlKey, UrlStatus.ACTIVE)).thenReturn(false);

        Optional<Boolean> result = urlManagerService.deleteUrlEntity(shortUrlKey);

        assertTrue(result.isPresent());
        assertFalse(result.get());
    }

//    @Test
//    public void testListURLEntity() {
//        // Mock user principal to return a specific user ID
//        when(Utils.getUserPrincipal()).thenReturn("user123");
//
//        // Mock the repository method to return a list of URLDocuments created by the user.
//        URLDocument url1 = new URLDocument("http://www.example1.com", new Date());
//        url1.setCreatedBy("user123");
//        URLDocument url2 = new URLDocument("http://www.example2.com", new Date());
//        url2.setCreatedBy("user123");
//        List<URLDocument> mockURLList = Arrays.asList(url1, url2);
//        when(urlRepository.findByCreatedBy("user123")).thenReturn(mockURLList);
//
//        List<URLDocument> resultList = urlManagerService.listURLEntity();
//
//        assertEquals(2, resultList.size());
//        assertEquals("http://www.example1.com", resultList.get(0).getOriginalUrl());
//        assertEquals("http://www.example2.com", resultList.get(1).getOriginalUrl());
//    }

//    @Test
//    public void testListURLEntityNoURLsFound() {
//        when(Utils.getUserPrincipal()).thenReturn("user123");
//
//        // Mock the repository method to return an empty list when no URLs are found for the user.
//        when(urlRepository.findByCreatedBy("user123")).thenReturn(Collections.emptyList());
//
//        List<URLDocument> resultList = urlManagerService.listURLEntity();
//        assertTrue(resultList.isEmpty());
//    }

}
