package util;

import java.util.Random;
import java.util.Scanner;

public class ConsoleIO {
    private final Scanner scanner;
    private final Random random;
    private final ConsoleUtils consoleUtils;

    public ConsoleIO() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.consoleUtils = new ConsoleUtils(scanner);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Random getRandom() {
        return random;
    }

    public ConsoleUtils getConsoleUtils() {
        return consoleUtils;
    }
}
