package com.github.dishavarshney.trimmy.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import in.turls.lib.models.url.UrlExpiry;
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
	
	@JsonProperty("expiry")
	private UrlExpiry urlExpiry;
}
