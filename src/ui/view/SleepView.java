package ui.view;

import java.util.Scanner;
import util.ConsoleUtils;

public class SleepView {
    private final Scanner scanner;
    private final ConsoleUtils consoleUtils;

    public SleepView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public boolean showSleepPrompt() {
        char choice = 0;

        do {
            consoleUtils.spaceConsole();
            System.out.print("Do you want to sleep? [y/n]: ");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.length() == 0) {
                System.out.println("Invalid input!");
                consoleUtils.pause();
                continue;
            }

            choice = input.charAt(0);

            if (choice != 'y' && choice != 'n') {
                System.out.println("Invalid input!");
                consoleUtils.pause();
            }
        } while (choice != 'y' && choice != 'n');

        return choice == 'y';
    }
}
