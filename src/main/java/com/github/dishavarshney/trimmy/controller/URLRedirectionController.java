package in.turls.lib.controllers.v1;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import in.turls.lib.services.interfaces.URLManagerService;

@RestController("/")
public class URLRedirectionController {

	private static final Logger LOG = LogManager.getLogger(URLRedirectionController.class);

	@Autowired
	private URLManagerService urlManagerService;

	@GetMapping
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("Hey There!! Nothing for you here I guess", HttpStatus.OK);
	}

	@GetMapping("/notfound")
	public ResponseEntity<String> noResourceFound() {
		LOG.error("Resource not found");
		return new ResponseEntity<String>("The resource you're looking for could not be located", HttpStatus.NOT_FOUND);
	}
	
	@Async
	@GetMapping("{shortUrlKey}")
	public CompletableFuture<ModelAndView> redirect(@PathVariable("shortUrlKey") final String shortUrlKey) {
		LOG.info("Redirection request for Short URL Key: {}", shortUrlKey);
		try {
			Optional<String> originalUrlOptional = urlManagerService.retrieveOriginalUrl(shortUrlKey);
			if (originalUrlOptional.isPresent() && StringUtils.hasText(originalUrlOptional.get())) {
				LOG.info("Redirecting to {}", originalUrlOptional.get());
				return CompletableFuture.completedFuture(new ModelAndView("redirect:" + originalUrlOptional.get()));
			}
		} catch (Exception e) {
			LOG.error("Error while redirecting", e);
		}
		return CompletableFuture.completedFuture(new ModelAndView("redirect:/notfound"));
	}

}
