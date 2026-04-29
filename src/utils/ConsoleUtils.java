package utils;

import java.util.Scanner;

public class ConsoleUtils {
	private Scanner sc;

	public ConsoleUtils(Scanner sc) {
		super();
		this.sc = sc;
	}

	public void spaceConsole() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
	
	public void pause () {
		System.out.print("Press ENTER to continue...");
		sc.nextLine();
	}
}
