package model;

public class User {
	private static final int MIN_USERNAME_LENGTH = 8;
	private static final int MIN_PASSWORD_LENGTH = 8;

	private String username;
	private String password;
	private boolean devMode = false;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isDevMode() {
		return devMode;
	}

	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}

	public static User parse(String line) {
		String[] parts = line.split("#");
		if (parts.length < 2) {
			return null;
		}
		return new User(parts[0], parts[1]);
	}

	public String format() {
		return username + "#" + password;
	}

	public static boolean isValidUsername(String candidate) {
		return candidate != null && candidate.trim().length() >= MIN_USERNAME_LENGTH;
	}

	public static boolean isValidPassword(String candidate) {
		if (candidate == null || candidate.length() < MIN_PASSWORD_LENGTH) {
			return false;
		}

		boolean hasLetter = false;
		boolean hasDigit = false;
		for (char character : candidate.toCharArray()) {
			if (Character.isLetter(character)) {
				hasLetter = true;
			}
			if (Character.isDigit(character)) {
				hasDigit = true;
			}
		}
		return hasLetter && hasDigit;
	}
}
