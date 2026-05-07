package commands;

import java.util.Scanner;
import models.Animal;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class SellAnimalCommand implements Command {
    private StoreViewModel storeViewModel;
    private PlayerViewModel playerViewModel;
    private Scanner sc;
    private ConsoleUtils consoleUtils;

    public SellAnimalCommand(StoreViewModel storeViewModel, PlayerViewModel playerViewModel, Scanner sc, ConsoleUtils consoleUtils) {
        this.storeViewModel = storeViewModel;
        this.playerViewModel = playerViewModel;
        this.sc = sc;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        if (playerViewModel.getAnimals().isEmpty()) {
            System.out.println("No Animals obtained yet!");
            consoleUtils.pause();
            return;
        }

        while (true) {
            int counter = 1;
            consoleUtils.spaceConsole();

            System.out.println("Sell Farm Animals");
            System.out.println();
            System.out.println("Money: $" + playerViewModel.getMoney());
            System.out.println();

            System.out.println("===================================================");
            System.out.printf("| %-3s | %-10s | %-15s | %-10s |\n", "No.", "Type", "Name", "Price");
            System.out.println("===================================================");

            for (Animal animal : playerViewModel.getAnimals()) {
                System.out.printf("| %-3d | %-10s | %-15s | %-10.1f |\n",
                        counter++, animal.getType(), animal.getName(), animal.getPrice());
            }

            System.out.println("===================================================");
            System.out.print("Choose Farm Animal to sell [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 0) return;

                if (storeViewModel.sellAnimal(choice)) {
                    System.out.println("Sucessfully sold a farm animal!");
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
