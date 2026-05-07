package commands;

import java.util.Scanner;
import models.items.Tool;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class BuyToolCommand implements Command {
    private StoreViewModel storeViewModel;
    private PlayerViewModel playerViewModel;
    private Scanner sc;
    private ConsoleUtils consoleUtils;

    public BuyToolCommand(StoreViewModel storeViewModel, PlayerViewModel playerViewModel, Scanner sc, ConsoleUtils consoleUtils) {
        this.storeViewModel = storeViewModel;
        this.playerViewModel = playerViewModel;
        this.sc = sc;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        while (true) {
            int counter = 1;
            consoleUtils.spaceConsole();
            System.out.println("Buy Tools");
            System.out.println();
            System.out.println("Money: $" + playerViewModel.getMoney());
            System.out.println();

            if (storeViewModel.getAvailableTools().isEmpty()) {
                System.out.println("You already buy all tools!");
                consoleUtils.pause();
                return;
            }

            System.out.println("=================================");
            System.out.printf("| %-3s | %-10s | %-10s |\n", "No.", "Name", "Price");
            System.out.println("=================================");

            for (Tool tool : storeViewModel.getAvailableTools()) {
                System.out.printf("| %-3s | %-10s | %-10s |\n", counter++, tool.getName(), tool.getPrice());
            }

            System.out.println("=================================");
            System.out.print("Choose Tool [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 0) return;

                if (storeViewModel.buyTool(choice)) {
                    System.out.println("Successfully buy a tool");
                } else {
                    if (choice >= 1 && choice < counter) {
                        System.out.println("Not enough money!");
                    } else {
                        System.out.println("Invalid choice!");
                    }
                }
                consoleUtils.pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
}
