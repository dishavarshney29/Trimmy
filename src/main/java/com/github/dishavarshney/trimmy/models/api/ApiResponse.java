package com.github.dishavarshney.trimmy.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dishavarshney.trimmy.constants.ApiRequestErrorCode;
import com.github.dishavarshney.trimmy.constants.ApiRequestStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {
	
	@JsonProperty("status")
	private ApiRequestStatus status;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("response")
	private T response;
	
	@JsonProperty("error")
	private ApiRequestErrorCode errorCode;

}
