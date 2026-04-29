package utils;

public class FreshnessUtils {
	public static double getFreshnessMultiplier (int freshness) {
		if (freshness == 1) 
			return 0.25;
		else if (freshness == 3) 
			return 0.5;
		else 
			return 1;
	}
}
