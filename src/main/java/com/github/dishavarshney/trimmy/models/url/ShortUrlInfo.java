package com.github.dishavarshney.trimmy.models.url;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ShortUrlInfo {
	
	@JsonProperty("key")
	private String key;
	
	@JsonProperty("completeUrl")
	private String completeUrl;
	
	@JsonProperty("expiry")
	private String expiry;

}
