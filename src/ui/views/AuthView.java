package ui.views;

import java.util.Scanner;

import models.User;
import services.UserFileHandlingService;
import services.UserValidationService;
import util.ConsoleUtils;

public class AuthView {
    private Scanner scanner;
    private ConsoleUtils consoleUtils;

    public AuthView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public User showMainMenu() {
        while (true) {
            consoleUtils.spaceConsole();
            System.out.println("                                                                                   ");
            System.out.println("    ▄▄▄                                          ▄▄▄              ▄▄ ▄▄             ");
            System.out.println("   ██▀▀█▄ █▄               █▄                   █▀██  ██  ██▀▀     ██ ██            ");
            System.out.println("   ██ ▄█▀▄██▄      ▄       ██                     ██  ██  ██       ██ ██            ");
            System.out.println("   ██▀▀█▄ ██ ▄▀▀█▄ ████▄▄████ ▄█▀█▄▀█▄ █▄ ██▀     ██  ██  ██ ▄▀▀█▄ ██ ██ ▄█▀█▄ ██ ██");
            System.out.println(" ▄ ██  ▄█ ██ ▄█▀██ ██   ██ ██ ██▄█▀ ██▄██▄██      ██▄ ██▄ ██ ▄█▀██ ██ ██ ██▄█▀ ██▄██");
            System.out.println(" ▀██████▀▄██▄▀█▄██▄█▀  ▄█▀███▄▀█▄▄▄  ▀██▀██▀      ▀████▀███▀▄▀█▄██▄██▄██▄▀█▄▄▄▄▄▀██▀");
            System.out.println("                                                                                 ██ ");
            System.out.println("                                                                               ▀▀▀  ");
            System.out.println();

            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Tutorial");
            System.out.println("4. Exit");
            System.out.print(">> ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        User loginUser = showLogin();
                        if (loginUser != null) return loginUser;
                        break;
                    case 2:
                        User registeredUser = showRegister();
                        if (registeredUser != null) return registeredUser;
                        break;
                    case 3:
                        TutorialView.showTutorial(scanner, consoleUtils);
                        break;
                    case 4:
                        System.out.println("Goodbye!");
                        System.exit(0);
                }
            } catch (Exception exception) {
                scanner.nextLine();
            }
        }
    }

    private User showLogin() {
        consoleUtils.spaceConsole();
        System.out.println(" _                 _       ");
        System.out.println("| |               (_)      ");
        System.out.println("| |     ___   __ _ _ _ __  ");
        System.out.println("| |    / _ \\ / _` | | '_ \\ ");
        System.out.println("| |___| (_) | (_| | | | | |");
        System.out.println("\\_____/\\___/ \\__, |_|_| |_|");
        System.out.println("              __/ |        ");
        System.out.println("             |___/         ");
        System.out.println();

        System.out.print("Username (0 to go back): ");
        String username = scanner.nextLine().trim();
        if (username.equals("0")) return null;

        System.out.print("Password (0 to go back): ");
        String password = scanner.nextLine().trim();
        if (password.equals("0")) return null;

        User user = UserFileHandlingService.authenticate(username, password);
        if (user != null) {
            System.out.println("Login successful!");
            consoleUtils.pause();
            return user;
        } else {
            System.out.println("Invalid username or password!");
            consoleUtils.pause();
            return null;
        }
    }

    private User showRegister() {
        consoleUtils.spaceConsole();
        System.out.println("______           _     _            ");
        System.out.println("| ___ \\         (_)   | |           ");
        System.out.println("| |_/ /___  __ _ _ ___| |_ ___ _ __ ");
        System.out.println("|    // _ \\/ _` | / __| __/ _ \\ '__|");
        System.out.println("| |\\ \\  __/ (_| | \\__ \\ ||  __/ |   ");
        System.out.println("\\_| \\_\\___|\\__, |_|___/\\__\\___|_|   ");
        System.out.println("            __/ |                   ");
        System.out.println("           |___/                    ");
        System.out.println();

        String username;
        while (true) {
            System.out.print("Username (>= 8 characters) [0 to go back]: ");
            username = scanner.nextLine().trim();

            if (username.equals("0")) return null;

            if (!UserValidationService.isValidUsername(username)) {
                System.out.println("Username must be at least 8 characters!");
                continue;
            }

            if (UserFileHandlingService.isUsernameTaken(username)) {
                System.out.println("Username already taken!");
                continue;
            }

            break;
        }

        String password;
        while (true) {
            System.out.print("Password (>= 8 characters, at least 1 letter and 1 number) [0 to go back]: ");
            password = scanner.nextLine().trim();

            if (password.equals("0")) return null;

            if (!UserValidationService.isValidPassword(password)) {
                System.out.println("Password must be at least 8 characters and contain at least 1 letter and 1 number!");
                continue;
            }

            break;
        }

        UserFileHandlingService.register(username, password);
        User user = new User(username, password);
        System.out.println("Registration successful!");
        consoleUtils.pause();
        return user;
    }
}
