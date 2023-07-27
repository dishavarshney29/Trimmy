package in.turls.lib.constants;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UrlExpiryUnit {

	MINUTES, DAYS, WEEKS, MONTHS, YEARS;
	
	private static final HashMap<String, UrlExpiryUnit> unitMap = new HashMap<>();
	
	static {
		unitMap.put(MINUTES.name(), MINUTES);
		unitMap.put(DAYS.name(), DAYS);
		unitMap.put(WEEKS.name(), WEEKS);
		unitMap.put(MONTHS.name(), MONTHS);
		unitMap.put(YEARS.name(), YEARS);
	}
	
	@JsonCreator
	public static UrlExpiryUnit forValue(String value) {
		if (!unitMap.containsKey(value)) {
			return null;
		}
		return unitMap.get(value);
	}
}
