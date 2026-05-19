package ui.view;

import java.util.List;
import java.util.Scanner;

import services.loader.TutorialPageLoader;
import util.ConsoleUtils;

public class TutorialView {
    private static final String TUTORIAL_PATH = "system_data/tutorial.txt";

    public static void showTutorial(Scanner scanner, ConsoleUtils consoleUtils) {
        List<String[]> pages = TutorialPageLoader.loadPages(TUTORIAL_PATH);
        if (pages.isEmpty()) {
            return;
        }

        int currentPage = 0;
        while (true) {
            renderPage(consoleUtils, pages, currentPage);

            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("n") && currentPage < pages.size() - 1) {
                currentPage++;
            } else if (input.equals("p") && currentPage > 0) {
                currentPage--;
            } else if (input.equals("q")) {
                return;
            }
        }
    }

    private static void renderPage(
        ConsoleUtils consoleUtils,
        List<String[]> pages,
        int currentPage
    ) {
        consoleUtils.spaceConsole();
        System.out.println("============================================================");
        System.out.println("               BTARDEW WALLEY - TUTORIAL");
        System.out.println("============================================================");
        System.out.println();

        String[] page = pages.get(currentPage);
        System.out.printf("  >> %s <<%n", page[0]);
        System.out.println("------------------------------------------------------------");

        for (int i = 1; i < page.length; i++) {
            System.out.printf("  %s%n", page[i]);
        }

        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.printf("  Page %d / %d\n", currentPage + 1, pages.size());
        System.out.println("------------------------------------------------------------");

        StringBuilder navigation = new StringBuilder("  ");
        if (currentPage > 0) {
            navigation.append("[P] Previous  ");
        }
        if (currentPage < pages.size() - 1) {
            navigation.append("[N] Next  ");
        }
        navigation.append("[Q] Back to Menu");
        System.out.println(navigation.toString());
        System.out.print("  >> ");
    }
}
