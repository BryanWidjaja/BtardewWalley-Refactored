package ui.view;

import java.util.Scanner;

import model.animal.Animal;
import util.ConsoleUtils;
import util.StringUtils;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class AnimalHarvestView {
    private static final int CHOICE_TAKE = 1;
    private static final int CHOICE_LEAVE = 2;

    private final Scanner scanner;
    private final ConsoleUtils consoleUtils;

    public AnimalHarvestView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public void showCollectAnimal(PlayerViewModel playerViewModel, MapViewModel mapViewModel) {
        Animal animal = mapViewModel.getPendingAnimal();
        if (animal == null || !animal.isHarvestable()) {
            return;
        }

        while (true) {
            renderPrompt(animal);
            int choice = readChoice();
            if (choice == CHOICE_LEAVE) {
                return;
            }
            if (choice == CHOICE_TAKE) {
                if (!ensureRequiredTool(playerViewModel, animal)) {
                    return;
                }
                mapViewModel.collectAnimalProduct(animal);
                return;
            }
            System.out.println("Invalid range number input!");
            consoleUtils.pause();
        }
    }

    private void renderPrompt(Animal animal) {
        System.out.printf("Want to take %s %s?\n",
                StringUtils.possessive(animal.getName()),
                animal.getAnimalProduct().getName());
        System.out.println("1. Take");
        System.out.println("2. Don't take");
        System.out.print(">> ");
    }

    private int readChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception exception) {
            scanner.nextLine();
            return -1;
        }
    }

    private boolean ensureRequiredTool(PlayerViewModel playerViewModel, Animal animal) {
        String requiredTool = animal.requiredToolName();
        if (requiredTool == null || playerViewModel.hasItem(requiredTool)) {
            return true;
        }

        System.out.printf("You don't have %s to get %s %s\n",
                articleFor(requiredTool),
                StringUtils.possessive(animal.getName()),
                animal.getAnimalProduct().getName());
        consoleUtils.pause();
        return false;
    }

    private String articleFor(String toolName) {
        if (toolName.equalsIgnoreCase("Shears")) {
            return toolName.toLowerCase();
        }
        return "a " + toolName.toLowerCase();
    }
}
