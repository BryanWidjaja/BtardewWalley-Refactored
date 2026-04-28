package services;

public class UserValidator {
	
	public static boolean isValidUsername(String username) {
		return username != null && username.trim().length() >= 8;
	}
	
	public static boolean isValidPassword(String password) {
		if (password == null || password.length() < 8) return false;
		boolean hasLetter = false;
		boolean hasDigit = false;
		for (char c : password.toCharArray()) {
			if (Character.isLetter(c)) hasLetter = true;
			if (Character.isDigit(c)) hasDigit = true;
		}
		return hasLetter && hasDigit;
	}
}
