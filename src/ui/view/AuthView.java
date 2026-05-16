package ui.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import model.User;
import services.UserRepository;
import util.ConsoleUtils;

public class AuthView {
    private static final String CANCEL_INPUT = "0";

    private final Scanner scanner;
    private final ConsoleUtils consoleUtils;
    private final UserRepository userRepository;
    private final Map<Integer, Supplier<User>> menuActions = new HashMap<>();

    public AuthView(Scanner scanner, ConsoleUtils consoleUtils, UserRepository userRepository) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
        this.userRepository = userRepository;

        menuActions.put(1, this::showLogin);
        menuActions.put(2, this::showRegister);
        menuActions.put(3, () -> {
            TutorialView.showTutorial(scanner, consoleUtils);
            return null;
        });
        menuActions.put(4, () -> {
            System.out.println("Goodbye!");
            System.exit(0);
            return null;
        });
    }

    public User showMainMenu() {
        while (true) {
            consoleUtils.spaceConsole();
            renderBanner();
            renderMenuOptions();
            Integer choice = readMenuChoice();
            if (choice == null) {
                continue;
            }

            User result = dispatchChoice(choice);
            if (result != null) {
                return result;
            }
        }
    }

    private void renderBanner() {
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
    }

    private void renderMenuOptions() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Tutorial");
        System.out.println("4. Exit");
        System.out.print(">> ");
    }

    private Integer readMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception exception) {
            scanner.nextLine();
            return null;
        }
    }

    private User dispatchChoice(int choice) {
        Supplier<User> action = menuActions.get(choice);
        if (action == null) {
            return null;
        }
        return action.get();
    }

    private User showLogin() {
        consoleUtils.spaceConsole();
        renderLoginBanner();

        String username = readOrCancel("Username (0 to go back): ");
        if (username == null) {
            return null;
        }
        String password = readOrCancel("Password (0 to go back): ");
        if (password == null) {
            return null;
        }

        User user = userRepository.authenticate(username, password);
        if (user != null) {
            System.out.println("Login successful!");
            consoleUtils.pause();
            return user;
        }
        System.out.println("Invalid username or password!");
        consoleUtils.pause();
        return null;
    }

    private void renderLoginBanner() {
        System.out.println(" _                 _       ");
        System.out.println("| |               (_)      ");
        System.out.println("| |     ___   __ _ _ _ __  ");
        System.out.println("| |    / _ \\ / _` | | '_ \\ ");
        System.out.println("| |___| (_) | (_| | | | | |");
        System.out.println("\\_____/\\___/ \\__, |_|_| |_|");
        System.out.println("              __/ |        ");
        System.out.println("             |___/         ");
        System.out.println();
    }

    private User showRegister() {
        consoleUtils.spaceConsole();
        renderRegisterBanner();

        String username = promptUsername();
        if (username == null) {
            return null;
        }
        String password = promptPassword();
        if (password == null) {
            return null;
        }

        userRepository.register(username, password);
        User user = new User(username, password);
        System.out.println("Registration successful!");
        consoleUtils.pause();
        return user;
    }

    private void renderRegisterBanner() {
        System.out.println("______           _     _            ");
        System.out.println("| ___ \\         (_)   | |           ");
        System.out.println("| |_/ /___  __ _ _ ___| |_ ___ _ __ ");
        System.out.println("|    // _ \\/ _` | / __| __/ _ \\ '__|");
        System.out.println("| |\\ \\  __/ (_| | \\__ \\ ||  __/ |   ");
        System.out.println("\\_| \\_\\___|\\__, |_|___/\\__\\___|_|   ");
        System.out.println("            __/ |                   ");
        System.out.println("           |___/                    ");
        System.out.println();
    }

    private String promptUsername() {
        while (true) {
            String username = readOrCancel("Username (>= 8 characters) [0 to go back]: ");
            if (username == null) {
                return null;
            }

            if (!User.isValidUsername(username)) {
                System.out.println("Username must be at least 8 characters!");
                continue;
            }
            if (userRepository.isUsernameTaken(username)) {
                System.out.println("Username already taken!");
                continue;
            }
            return username;
        }
    }

    private String promptPassword() {
        while (true) {
            String password = readOrCancel("Password (>= 8 characters, at least 1 letter and 1 number) [0 to go back]: ");
            if (password == null) {
                return null;
            }

            if (!User.isValidPassword(password)) {
                System.out.println("Password must be at least 8 characters and contain at least 1 letter and 1 number!");
                continue;
            }
            return password;
        }
    }

    private String readOrCancel(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.equals(CANCEL_INPUT)) {
            return null;
        }
        return input;
    }
}
