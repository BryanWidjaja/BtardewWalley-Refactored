package util;

public class StringUtils {

	public static String possessive(String name) {
		if (name.toLowerCase().endsWith("s")) {
			return name + "'";
		}
		return name + "'s";
	}
}
