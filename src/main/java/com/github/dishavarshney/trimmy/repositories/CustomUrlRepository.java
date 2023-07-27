package com.github.dishavarshney.trimmy.repositories;


import com.github.dishavarshney.trimmy.constants.UrlStatus;

import java.util.Date;

public interface CustomUrlRepository {
	
	public void updateByValidTillAndStatus(final Date currentDate, final UrlStatus oldStatus, final UrlStatus newStatus);
	
	public boolean deleteByShortUrlKeyAndStatus(final String shortUrlKey, final UrlStatus status);

}
