package com.github.dishavarshney.trimmy.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dishavarshney.trimmy.models.url.UrlExpiry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequest {
	
	@JsonProperty("url")
	@NotNull
	@Length(min = 8, max = 200)
	private String longUrl;

	@JsonProperty("customShortUrl")
	@Length(max = 7)
	private String customShortUrl;
	
	@JsonProperty("expiry")
	private UrlExpiry urlExpiry;
}
