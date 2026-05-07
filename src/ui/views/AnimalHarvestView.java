package ui.views;

import java.util.Scanner;
import models.Animal;
import util.ConsoleUtils;
import util.StringUtils;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class AnimalHarvestView {
    private Scanner scanner;
    private ConsoleUtils consoleUtils;

    public AnimalHarvestView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public void showCollectAnimal(PlayerViewModel playerViewModel, MapViewModel mapViewModel) {
        Animal animal = mapViewModel.getPendingAnimal();
        if (animal == null || !animal.isHarvestable()) {
            return;
        }

        int choice = 0;
        while (true) {
            System.out.printf("Want to take %s %s?\n", StringUtils.possessive(animal.getName()), animal.getAnimalProduct());
            System.out.println("1. Take");
            System.out.println("2. Don't take");
            System.out.print(">> ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    if (animal.getSymbol() == 'C' && !playerViewModel.hasItem("Bucket")) {
                        System.out.printf(
                                "You don't have a bucket to get %s %s\n",
                                StringUtils.possessive(animal.getName()),
                                animal.getAnimalProduct()
                        );
                        consoleUtils.pause();
                        return;
                    } else if (animal.getSymbol() == 'S' && !playerViewModel.hasItem("Shears")) {
                        System.out.printf(
                                "You don't have shears to get %s %s\n",
                                StringUtils.possessive(animal.getName()),
                                animal.getAnimalProduct()
                        );
                        consoleUtils.pause();
                        return;
                    } else {
                        mapViewModel.collectAnimalProduct(animal);
                        break;
                    }
                }

                if (choice == 2) {
                    break;
                }

                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid range number input!");
                    consoleUtils.pause();
                }
            } catch (Exception exception) {
                scanner.nextLine();
            }
        }
    }
}
