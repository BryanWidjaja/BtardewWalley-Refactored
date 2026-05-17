package ui.view;

import java.util.List;
import java.util.Scanner;

import model.item.plantseed.PlantSeed;
import util.ConsoleUtils;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class PlantView {
    private static final String HOE_NAME = "Hoe";
    private static final int CANCEL_CHOICE = 0;

    private final Scanner scanner;
    private final ConsoleUtils consoleUtils;

    public PlantView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public void showPlantPrompt(PlayerViewModel playerViewModel, MapViewModel mapViewModel) {
        if (!playerViewModel.hasItem(HOE_NAME)) {
            System.out.println("You need a Hoe to plant! Buy one from the tool store.");
            consoleUtils.pause();
            return;
        }

        List<PlantSeed> playerSeeds = playerViewModel.getPlayerSeeds();
        if (playerSeeds.isEmpty()) {
            System.out.println("No seeds in your inventory!");
            consoleUtils.pause();
            return;
        }

        displaySeeds(playerSeeds, playerViewModel);

        int choice = readSeedChoice(playerSeeds.size());
        if (choice == CANCEL_CHOICE) {
            return;
        }

        PlantSeed selectedSeed = playerSeeds.get(choice - 1);
        mapViewModel.plantSeed(selectedSeed, mapViewModel.getPendingX(), mapViewModel.getPendingY());
    }

    private void displaySeeds(List<PlantSeed> seeds, PlayerViewModel playerViewModel) {
        for (int i = 0; i < seeds.size(); i++) {
            PlantSeed seed = seeds.get(i);
            int quantity = quantityOf(seed, playerViewModel);
            System.out.printf("%d. %s Seed - %d\n", i + 1, seed.getName(), quantity);
        }
    }

    private int quantityOf(PlantSeed seed, PlayerViewModel playerViewModel) {
        return playerViewModel.getInventory().stream()
                .filter(item -> item.getItem() instanceof PlantSeed
                        && item.getItem().getName().equals(seed.getName()))
                .mapToInt(item -> item.getQuantity())
                .findFirst()
                .orElse(0);
    }

    private int readSeedChoice(int maxChoice) {
        int choice = -1;
        do {
            System.out.printf("Which seed do you want to plant [1-%d] [0 to exit]: \n", maxChoice);
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception exception) {
                scanner.nextLine();
            }
        } while (choice < 0 || choice > maxChoice);
        return choice;
    }
}
