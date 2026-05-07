package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import models.User;

public class UserFileHandlingService {
	public static boolean isUsernameTaken(String username) {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader("users.txt"))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split("#");
				if (parts[0].equals(username)) {
					return true;
				}
			}
		} catch (FileNotFoundException exception) {
		} catch (IOException exception) {
			System.out.println("Error reading users file");
		}
		return false;
	}
	
	public static User authenticate(String username, String password) {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader("users.txt"))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split("#");
				if (parts[0].equals(username) && parts[1].equals(password)) {
					return new User(username, password);
				}
			}
		} catch (FileNotFoundException exception) {
		} catch (IOException exception) {
			System.out.println("Error reading users file");
		}
		return null;
	}
	
	public static void register(String username, String password) {
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.txt", true))) {
			bufferedWriter.write(username + "#" + password);
			bufferedWriter.newLine();
		} catch (IOException exception) {
			System.out.println("Error writing users file");
		}
	}
}
