package model;

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

	public static User parse(String line) {
		String[] parts = line.split("#");
		if (parts.length < 2) return null;
		return new User(parts[0], parts[1]);
	}

	public String format() {
		return username + "#" + password;
	}
}
