package com.github.dishavarshney.trimmy.repositories.impls;

import com.github.dishavarshney.trimmy.constants.UrlStatus;
import com.github.dishavarshney.trimmy.models.url.URLDocument;
import com.github.dishavarshney.trimmy.repositories.CustomUrlRepository;
import com.mongodb.bulk.BulkWriteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

public class CustomUrlRepositoryImpl implements CustomUrlRepository {
	
	private static final Logger LOG = LogManager.getLogger(CustomUrlRepository.class);
	
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public void updateByValidTillAndStatus(Date currentDate, UrlStatus oldStatus, UrlStatus newStatus) {
		Criteria criteria = new Criteria();
		criteria.and("validTill").lte(currentDate).and("status").is(oldStatus);
		Query query = new Query(criteria);
		LOG.debug("Custom Update Query: {}", query);
		Update update = new Update();
		update.set("status", newStatus);
		update.set("expiredAt", currentDate);
		BulkOperations bulkOps = mongoOperations.bulkOps(BulkMode.UNORDERED, URLDocument.class);
		bulkOps.updateMulti(query, update);
		BulkWriteResult result = bulkOps.execute();
		LOG.debug("Documents Matched: {}", result.getMatchedCount());
		LOG.debug("Documents Updated: {}", result.getModifiedCount());
	}

	@Override
	public boolean deleteByShortUrlKeyAndStatus(String shortUrlKey, UrlStatus status) {
		Criteria criteria = new Criteria();
		criteria.and("shortUrlKey").is(shortUrlKey).and("status").is(status);
		Query query = new Query(criteria);
		LOG.debug("Custom Delete Query: {}", query);
		if (mongoOperations.exists(query, URLDocument.class)) {
			mongoOperations.remove(query, URLDocument.class);
			LOG.debug("Deletion Successful");
			return true;
		}
		LOG.warn("Couldn't find any entity to delete with ShortUrl: {} and Status: {}", shortUrlKey, status);
		return false;
	}

}
