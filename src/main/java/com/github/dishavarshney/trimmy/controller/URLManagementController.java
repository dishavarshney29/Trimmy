//package com.github.dishavarshney.trimmy.controller;
//
//import com.github.dishavarshney.trimmy.constants.ApiRequestErrorCode;
//import com.github.dishavarshney.trimmy.constants.ApiRequestStatus;
//import com.github.dishavarshney.trimmy.constants.UrlExpiryUnit;
//import com.github.dishavarshney.trimmy.exceptions.InvalidUrlExpiryUnit;
//import com.github.dishavarshney.trimmy.exceptions.InvalidUrlExpiryValue;
//import com.github.dishavarshney.trimmy.models.api.ApiRequest;
//import com.github.dishavarshney.trimmy.models.api.ApiResponse;
//import com.github.dishavarshney.trimmy.models.url.ShortUrlInfo;
//import com.github.dishavarshney.trimmy.models.url.UrlExpiry;
//import com.github.dishavarshney.trimmy.service.interfaces.URLManagerService;
//import com.github.dishavarshney.trimmy.validators.ApiRequestValidator;
//import lombok.NonNull;
//import org.apache.commons.validator.routines.UrlValidator;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.NoSuchElementException;
//import java.util.Optional;
//import java.util.concurrent.CompletableFuture;
//
///**
// * @author disha.varshney
// */
//
//@RestController
//@RequestMapping("/v1/url")
//public class URLManagementController {
//
//	private static final Logger LOG = LogManager.getLogger(URLManagementController.class);
//	private static final UrlValidator URL_VALIDATOR = new UrlValidator();
//
//	@Autowired
//	private URLManagerService urlManagerService;
//
//	private static final String ALLOWED_ORIGINS = "*";
//
//	@Async("asyncExecutor")
//	@PostMapping
//	@CrossOrigin(origins = ALLOWED_ORIGINS, allowedHeaders = "*")
//	public CompletableFuture<ResponseEntity<?>> createShortUrl(
//			@RequestBody @Validated ApiRequest apiRequest) {
//
//		LOG.info("Incoming Request for creating short URL: {}", apiRequest);
//		ApiResponse<ShortUrlInfo> response = new ApiResponse<>();
//		String longUrl = apiRequest.getLongUrl();
//		if (!StringUtils.hasText(longUrl) || !URL_VALIDATOR.isValid(longUrl)) {
//			LOG.error("Invalid Long URL:{}", longUrl);
//			response.setStatus(ApiRequestStatus.FAILURE);
//			response.setMessage("Invalid URL. Please ensure the URL is not missing any URL scemes like http or https");
//			response.setErrorCode(ApiRequestErrorCode.INVALID_URL_SCHEME);
//			return CompletableFuture
//					.completedFuture(new ResponseEntity<ApiResponse<ShortUrlInfo>>(response, HttpStatus.BAD_REQUEST));
//		}
//
//		try {
//			ApiRequestValidator.validateApiRequest(apiRequest);
//		} catch (InvalidUrlExpiryUnit | InvalidUrlExpiryValue e) {
//			LOG.error("Error while validating ApiRequest", e);
//			String message = e.getMessage();
//			response.setStatus(ApiRequestStatus.FAILURE);
//			ApiRequestErrorCode errorCode = null;
//			if (e instanceof InvalidUrlExpiryUnit) {
//				errorCode = ApiRequestErrorCode.INVALID_URL_EXPIRY_UNIT;
//			} else {
//				errorCode = ApiRequestErrorCode.INVALID_URL_EXPIRY_VALUE;
//			}
//			response.setErrorCode(errorCode);
//			response.setMessage(message);
//			response.setResponse(null);
//			return CompletableFuture
//					.completedFuture(new ResponseEntity<ApiResponse<ShortUrlInfo>>(response, HttpStatus.BAD_REQUEST));
//		}
//
//		UrlExpiryUnit unit = UrlExpiryUnit.MONTHS;
//		Integer value = 1;
//
//		if (apiRequest.getUrlExpiry() != null) {
//			UrlExpiry urlExpiry = apiRequest.getUrlExpiry();
//			unit = urlExpiry.getUnit();
//			value = urlExpiry.getValue();
//		}
//		ShortUrlInfo shortUrlInfo = urlManagerService.createShortUrlKey(apiRequest.getLongUrl(), unit, value).get();
//
//		response.setStatus(ApiRequestStatus.SUCCESS);
//		response.setErrorCode(null);
//		response.setMessage("Successfully created short URL");
//		response.setResponse(shortUrlInfo);
//		LOG.debug("Outgoing Response: {}", response);
//		return CompletableFuture.completedFuture(new ResponseEntity<ApiResponse<ShortUrlInfo>>(response, HttpStatus.OK));
//	}
//
//
//	@Async("asyncExecutor")
//	@DeleteMapping("/{id}")
//	public CompletableFuture<ResponseEntity<?>> deleteUrlEntry(@PathVariable("id") @NonNull String id) {
//
//		LOG.info("Incoming request for deleting URL entry for Short URL Key: {}", id);
//		ApiResponse<Void> apiResponse = new ApiResponse<>();
//
//		try {
//			if (StringUtils.isEmpty(id)) {
//				apiResponse.setStatus(ApiRequestStatus.FAILURE);
//				apiResponse.setMessage("Invalid ID");
//				apiResponse.setErrorCode(ApiRequestErrorCode.INVALID_SHORT_URL_ID);
//				return CompletableFuture
//						.completedFuture(new ResponseEntity<ApiResponse<Void>>(apiResponse, HttpStatus.BAD_REQUEST));
//			}
//
//			Optional<Boolean> deleted = urlManagerService.deleteUrlEntity(id);
//
//			if (deleted.isPresent() && deleted.get().equals(Boolean.TRUE)) {
//				LOG.info("Successfully deleted URL entry with Short URL Key: {}", id);
//				apiResponse.setStatus(ApiRequestStatus.SUCCESS);
//				apiResponse.setMessage("Successfully deleted URL");
//				return CompletableFuture.completedFuture(new ResponseEntity<ApiResponse<Void>>(apiResponse, HttpStatus.OK));
//			}
//		} catch (NoSuchElementException e) {
//			LOG.error("No Such URL Entity found with Short URL Key: {}", id);
//		}
//
//		apiResponse.setStatus(ApiRequestStatus.FAILURE);
//		apiResponse.setMessage("Failed to delete URL since given short URL doesn't exist");
//		apiResponse.setErrorCode(ApiRequestErrorCode.INVALID_SHORT_URL_ID);
//		return CompletableFuture.completedFuture(new ResponseEntity<ApiResponse<Void>>(apiResponse, HttpStatus.OK));
//
//	}
//
//}
