package in.turls.lib.validators;

import java.util.Optional;

import in.turls.lib.constants.UrlExpiryUnit;
import in.turls.lib.exceptions.InvalidUrlExpiryUnit;
import in.turls.lib.exceptions.InvalidUrlExpiryValue;
import in.turls.lib.models.api.ApiRequest;
import in.turls.lib.models.url.UrlExpiry;

public class ApiRequestValidator {
	
	public static void validateApiRequest(final ApiRequest apiRequest) throws InvalidUrlExpiryUnit, InvalidUrlExpiryValue {
		
		if (apiRequest.getUrlExpiry() != null) {
			validateUrlExpiry(apiRequest.getUrlExpiry());
		}
		
	}
	
	private static void validateUrlExpiry(final UrlExpiry urlExpiry) throws InvalidUrlExpiryUnit, InvalidUrlExpiryValue {
		
		Optional<UrlExpiryUnit> unitOptional = Optional.ofNullable(urlExpiry.getUnit());
		Optional<Integer> valueOptional = Optional.ofNullable(urlExpiry.getValue());
		
		if (unitOptional.isEmpty()) {
			throw new InvalidUrlExpiryUnit("Invalid UNIT for expiry");
		}
		
		if (valueOptional.isEmpty()) {
			throw new InvalidUrlExpiryValue("Invalid VALUE for expiry");
		}
		
		UrlExpiryUnit unit = urlExpiry.getUnit();
		Integer value = urlExpiry.getValue();
		
		switch (unit) {
		case DAYS:
			if (value.intValue() < 3) {
				throw new InvalidUrlExpiryValue("When UNIT selected is DAYS, value cannot be less than 3");
			}
			break;
		case WEEKS:
		case MONTHS:
			String message;
			if (value.intValue() < 1) {
				if (UrlExpiryUnit.WEEKS.equals(unit)) {
					message = "When URL Expiry \"unit\" selected is WEEKS, \"value\" cannot be less than 3";
				} else {
					message = "When URL Expiry \"unit\" selected is MONTHS, \"value\" cannot be less than 1";
				} 
				throw new InvalidUrlExpiryValue(message);
			}
			break;
		case YEARS:
			if (value.intValue() <=0 || value.intValue() > 1) {
				message = "When URL Expiry \"unit\" selected is YEARS, \"value\" cannot be anything but 1";
				throw new InvalidUrlExpiryValue(message);
			}
			break;
		default:
			throw new InvalidUrlExpiryUnit("Unexpected value: " + unit);
		}
		
	}

}
