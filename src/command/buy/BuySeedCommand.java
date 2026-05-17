package command.buy;

import java.util.List;
import java.util.Scanner;

import model.item.plantseed.PlantSeed;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class BuySeedCommand extends BuyItemCommand<PlantSeed> {

    public BuySeedCommand(
        StoreViewModel storeViewModel,
        PlayerViewModel playerViewModel,
        Scanner sc,
        ConsoleUtils consoleUtils
    ) {
        super(storeViewModel, playerViewModel, sc, consoleUtils);
    }

    @Override
    protected List<PlantSeed> getItems() {
        return storeViewModel.getAvailableSeeds();
    }

    @Override
    protected void displayTitle() {
        System.out.println("Buy Seeds");
    }

    @Override
    protected void displayTable(List<PlantSeed> items) {
        System.out.println("================================================");
        System.out.printf("| %-3s | %-10s | %-12s | %-10s |\n", "No.", "Name", "Growth Time", "Price");
        System.out.println("================================================");

        int counter = 1;
        for (PlantSeed seed : items) {
            System.out.printf(
                "| %-3d | %-10s | %-12d | %-10.1f |\n",
                counter++,
                seed.getName(),
                seed.getGrowthTime(),
                seed.getPrice()
            );
        }
        System.out.println("================================================");
    }

    @Override
    protected String getItemTypeName() {
        return "Seed";
    }

    @Override
    protected PlantSeed getItem(int choice) {
        return storeViewModel.getSeed(choice);
    }

    @Override
    protected void performPurchase(int choice, PlantSeed seed) {
        int quantity = getQuantity(seed.getName());
        if (storeViewModel.buySeed(choice, quantity)) {
            System.out.printf("Successfully bought %d %s Seeds\n", quantity, seed.getName());
        } else {
            System.out.println("Not enough money!");
        }
    }

    private int getQuantity(String seedName) {
        int quantity = -1;
        do {
            try {
                System.out.printf("How many %s seeds would you like to purchase: ", seedName);
                quantity = sc.nextInt();
                sc.nextLine();
            } catch (Exception exception) {
                sc.nextLine();
            }
        } while (quantity <= 0);
        return quantity;
    }
}
