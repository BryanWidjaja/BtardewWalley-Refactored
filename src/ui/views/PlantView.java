package ui.views;

import java.util.List;
import java.util.Scanner;
import models.PlayerItem;
import models.items.PlantSeed;
import util.ConsoleUtils;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class PlantView {
    private Scanner scanner;
    private ConsoleUtils consoleUtils;

    public PlantView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public void showPlantPrompt(PlayerViewModel playerViewModel, MapViewModel mapViewModel) {
        if (!playerViewModel.hasItem("Hoe")) {
            System.out.println("You need a Hoe to plant! Buy one from the tool store.");
            consoleUtils.pause();
            return;
        }

        int counter = 1;
        List<PlantSeed> playerSeeds = playerViewModel.getPlayerSeeds();

        for (PlayerItem item : playerViewModel.getInventory()) {
            if (item.getItem() instanceof PlantSeed) {
                System.out.printf("%d. %s Seed - %d\n", counter++,
                        item.getItem().getName(), item.getQuantity());
            }
        }

        if (playerSeeds.isEmpty()) {
            System.out.println("No seeds in your inventory!");
            consoleUtils.pause();
            return;
        }

        int choice = -1;
        do {
            System.out.printf("Which seed do you want to plant [1-%d] [0 to exit]: \n",
                    playerSeeds.size());
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception exception) {
                scanner.nextLine();
            }
        } while (choice < 0 || choice > playerSeeds.size());

        if (choice == 0) {
            return;
        }

        PlantSeed selectedSeed = playerSeeds.get(choice - 1);
        mapViewModel.plantSeed(selectedSeed, mapViewModel.getPendingX(), mapViewModel.getPendingY());
    }
}
