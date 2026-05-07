package commands;

import java.util.Scanner;
import models.Animal;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class BuyAnimalCommand implements Command {
    private StoreViewModel storeViewModel;
    private PlayerViewModel playerViewModel;
    private Scanner sc;
    private ConsoleUtils consoleUtils;

    public BuyAnimalCommand(StoreViewModel storeViewModel, PlayerViewModel playerViewModel, Scanner sc, ConsoleUtils consoleUtils) {
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

            System.out.println("Buy Farm Animals");
            System.out.println();
            System.out.println("Money: $" + playerViewModel.getMoney());
            System.out.println();

            System.out.println("================================================");
            System.out.printf("| %-3s | %-10s | %-12s | %-10s |\n", "No.", "Name", "Harvest Rate", "Price");
            System.out.println("================================================");

            for (Animal animal : storeViewModel.getAvailableAnimals()) {
                System.out.printf("| %-3d | %-10s | %-12d | %-10.1f |\n",
                        counter++, animal.getType(), animal.getHarvestRate(), animal.getPrice());
            }

            System.out.println("================================================");
            System.out.print("Choose Farm Animals [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                    Animal selectedAnimal = storeViewModel.getAvailableAnimals().get(choice - 1);

                    if (playerViewModel.getMoney() >= selectedAnimal.getPrice()) {
                        String name = null;
                        boolean nameTaken;

                        do {
                            System.out.print("Input new farm animal's name [<= 15 characters]: ");
                            name = sc.nextLine();

                            nameTaken = playerViewModel.isAnimalNameTaken(name);
                            if (nameTaken) {
                                System.out.println("Name already taken!");
                            }
                        } while (name.length() > 15 || name.trim().isEmpty() || nameTaken);

                        if (storeViewModel.buyAnimal(choice, name)) {
                            System.out.println("Successfully bought a farm animal");
                        }
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
