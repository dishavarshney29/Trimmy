package com.github.dishavarshney.trimmy.service.interfaces;

import com.github.dishavarshney.trimmy.constants.UrlExpiryUnit;
import com.github.dishavarshney.trimmy.exceptions.InvalidCustomShortUrl;
import com.github.dishavarshney.trimmy.models.url.ShortUrlInfo;
import com.github.dishavarshney.trimmy.models.url.URLDocument;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface URLManagerService {

	public Optional<ShortUrlInfo> createShortUrlKey(final String longUrl, final String customShortUrl, final UrlExpiryUnit expiryUnit, final Integer expiryValue) throws NoSuchElementException, DuplicateKeyException, InvalidCustomShortUrl;
	public Optional<String> retrieveOriginalUrl(final String shortUrlKey) throws NoSuchElementException;
	public Optional<Boolean> deleteUrlEntity(final String shortUrlKey) throws NoSuchElementException;
	public List<URLDocument> listURLEntity();
}
