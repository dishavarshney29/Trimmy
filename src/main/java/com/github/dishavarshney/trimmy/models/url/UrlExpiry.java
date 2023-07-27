package com.github.dishavarshney.trimmy.models.url;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dishavarshney.trimmy.constants.UrlExpiryUnit;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UrlExpiry {
	
	@JsonProperty("unit")
	private UrlExpiryUnit unit;

	@JsonProperty("value")
	private Integer value;

}
