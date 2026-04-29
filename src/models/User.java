package models;

public class User {
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
	
	public boolean isValidUsername(String username) {
		return username != null && username.trim().length() >= 8;
	}
	
	public boolean isValidPassword(String password) {
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
