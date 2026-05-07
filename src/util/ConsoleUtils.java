package util;

import java.util.Scanner;

public class ConsoleUtils {
	private Scanner scanner;

	public ConsoleUtils(Scanner scanner) {
		super();
		this.scanner = scanner;
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
