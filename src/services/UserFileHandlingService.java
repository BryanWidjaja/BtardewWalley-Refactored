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
		try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("#");
				if (parts[0].equals(username)) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			System.out.println("Error reading users file");
		}
		return false;
	}
	
	public static User authenticate(String username, String password) {
		try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("#");
				if (parts[0].equals(username) && parts[1].equals(password)) {
					return new User(username, password);
				}
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			System.out.println("Error reading users file");
		}
		return null;
	}
	
	public static void register(String username, String password) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
			bw.write(username + "#" + password);
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Error writing users file");
		}
	}
}
