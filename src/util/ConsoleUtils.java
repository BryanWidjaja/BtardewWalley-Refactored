package util;

import java.util.Random;
import java.util.Scanner;

public class ConsoleUtils {
	private final Scanner scanner;
	private final Random random;

	public ConsoleUtils() {
		this.scanner = new Scanner(System.in);
		this.random = new Random();
	}

	public ConsoleUtils(Scanner scanner) {
		this.scanner = scanner;
		this.random = new Random();
	}

	public Scanner getScanner() {
		return scanner;
	}

	public Random getRandom() {
		return random;
	}

	public void spaceConsole() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

	public void pause() {
		System.out.print("Press ENTER to continue...");
		scanner.nextLine();
	}
}
