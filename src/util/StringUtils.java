package util;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {

	public static final String RESET = "[0m";
	public static final String GREEN = "[32m";
	public static final String YELLOW = "[33m";
	public static final String WHITE_BRIGHT = "[97m";
	public static final String BROWN = "[38;5;94m";

	private static final Map<Character, String> TILE_COLORS = new HashMap<>();
	static {
		TILE_COLORS.put('P', GREEN);
		TILE_COLORS.put('c', YELLOW);
		TILE_COLORS.put('C', BROWN);
		TILE_COLORS.put('S', WHITE_BRIGHT);
	}

	public static String colorize(char character) {
		String color = TILE_COLORS.get(character);
		if (color == null) {
			return String.valueOf(character);
		}
		return color + character + RESET;
	}

	public static String possessive(String name) {
		if (name.toLowerCase().endsWith("s")) {
			return name + "'";
		}
		return name + "'s";
	}
}
