package com.github.dishavarshney.trimmy.service.impls;

import com.github.dishavarshney.trimmy.service.interfaces.CounterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class CounterServiceImpl implements CounterService {

	private static final Logger LOGGER = LogManager.getLogger(CounterServiceImpl.class);
	
	private AtomicLong counter;

	@Value("${counter_file.location}")
	private String counterFilePath;

	@Override
	public Long getNextCounterNumber() {
		return counter.getAndIncrement();
	}
	
	
	@PostConstruct
	public synchronized void init() throws IOException, URISyntaxException {
		LOGGER.info("Reading counter value from: {}", counterFilePath);
		String counterValue = new String(Files.readAllBytes(Paths.get(counterFilePath)));
		LOGGER.info("Picked Counter Value: {}", counterValue);
		counter = new AtomicLong(Long.valueOf(counterValue));
	}
	
	@PreDestroy
	public synchronized void cleanup() throws URISyntaxException {
		LOGGER.info("Cleaning up Counter Service");
		LOGGER.info("Writing counter value {} to file at {}", counter.toString(), counterFilePath);
		Path path = Paths.get(new URI(counterFilePath));
		try(BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			writer.write(counter.toString());
		} catch (IOException e) {
			LOGGER.error("Error while writing counter value to file", e);
		}
	}

}
