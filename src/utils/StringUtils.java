package utils;

public class StringUtils {
	
	public static final String RESET = "\u001B[0m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String WHITE_BRIGHT = "\u001B[97m";
	public static final String BROWN = "\u001B[38;5;94m";

	public static String colorize(char c) {
		switch (c) {
			case 'P': return GREEN + c + RESET;
			case 'c': return YELLOW + c + RESET;
			case 'C': return BROWN + c + RESET;
			case 'S': return WHITE_BRIGHT + c + RESET;
			default: return String.valueOf(c);
		}
	}
	
	public static String possessive(String name) {
		if (name.toLowerCase().endsWith("s")) {
			return name + "'";
		}
		return name + "'s";
	}
}
