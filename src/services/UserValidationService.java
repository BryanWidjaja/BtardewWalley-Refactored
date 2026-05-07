package services;

public class UserValidationService {
	public static boolean isValidUsername(String username) {
		return username != null && username.trim().length() >= 8;
	}
	
	public static boolean isValidPassword(String password) {
		if (password == null || password.length() < 8) {
            return false;
        }

		boolean hasLetter = false;
		boolean hasDigit = false;

		for (char character : password.toCharArray()) {
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
