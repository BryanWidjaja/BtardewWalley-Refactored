package services.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserRepository {
    private final String filePath;

    public UserRepository(String filePath) {
        this.filePath = filePath;
    }

    public List<User> loadAll() {
        List<User> users = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                User user = User.parse(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (FileNotFoundException exception) {
        } catch (IOException exception) {
            System.out.println("Error reading users file");
        }
        return users;
    }

    public boolean isUsernameTaken(String username) {
        return loadAll().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    public User authenticate(String username, String password) {
        return loadAll().stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void register(String username, String password) {
        User user = new User(username, password);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.write(user.format());
            bufferedWriter.newLine();
        } catch (IOException exception) {
            System.out.println("Error writing users file");
        }
    }
}
