package in.turls.lib.scheduler;

import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import in.turls.lib.constants.UrlStatus;
import in.turls.lib.repositories.URLRepository;

@Component
public class ExpireDocumentScheduler {
	
	private static final Logger LOG = LogManager.getLogger(ExpireDocumentScheduler.class);
	
	@Autowired
	private URLRepository urlRepository;

	@Scheduled(fixedDelay = 60000)
	public void expireDocuments() {
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		LOG.info("Scheduler running to expire documents at: {}", currentDate);
		urlRepository.updateByValidTillAndStatus(currentDate, UrlStatus.ACTIVE, UrlStatus.EXPIRED);
	}
	
}
