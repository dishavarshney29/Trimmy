package in.turls.lib.models.url;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import in.turls.lib.constants.UrlExpiryUnit;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UrlExpiry {
	
	@JsonProperty("unit")
	private UrlExpiryUnit unit;

	@JsonProperty("value")
	private Integer value;

}
