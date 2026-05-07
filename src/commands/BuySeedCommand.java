package commands;

import java.util.Scanner;
import models.items.PlantSeed;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class BuySeedCommand implements Command {
    private StoreViewModel storeViewModel;
    private PlayerViewModel playerViewModel;
    private Scanner sc;
    private ConsoleUtils consoleUtils;

    public BuySeedCommand(StoreViewModel storeViewModel, PlayerViewModel playerViewModel, Scanner sc, ConsoleUtils consoleUtils) {
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

            System.out.println("Buy Seeds");
            System.out.println();
            System.out.println("Money: $" + playerViewModel.getMoney());
            System.out.println();

            System.out.println("================================================");
            System.out.printf("| %-3s | %-10s | %-12s | %-10s |\n", "No.", "Name", "Growth Time", "Price");
            System.out.println("================================================");

            for (PlantSeed seed : storeViewModel.getAvailableSeeds()) {
                System.out.printf("| %-3d | %-10s | %-12d | %-10.1f |\n",
                        counter++, seed.getName(), seed.getGrowthTime(), seed.getPrice());
            }

            System.out.println("================================================");
            System.out.print("Choose Seed [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                    PlantSeed selectedSeed = storeViewModel.getAvailableSeeds().get(choice - 1);
                    int quantity = -1;

                    do {
                        try {
                            System.out.printf("How many %s seeds would you like to purchase: ", selectedSeed.getName());
                            quantity = sc.nextInt();
                            sc.nextLine();
                        } catch (Exception e) {
                            sc.nextLine();
                        }
                    } while (quantity <= 0);

                    if (storeViewModel.buySeed(choice, quantity)) {
                        System.out.printf("Successfully bought %d %s Seeds\n",
                                quantity, selectedSeed.getName());
                    } else {
                        System.out.println("Not enough money!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
                consoleUtils.pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
}
