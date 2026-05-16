package command.sell;

import java.util.List;
import java.util.Scanner;

import model.animal.Animal;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class SellAnimalCommand extends SellItemCommand<Animal> {

    public SellAnimalCommand(
        StoreViewModel storeViewModel,
        PlayerViewModel playerViewModel,
        Scanner sc,
        ConsoleUtils consoleUtils
    ) {
        super(storeViewModel, playerViewModel, sc, consoleUtils);
    }

    @Override
    protected List<Animal> getItems() {
        return playerViewModel.getAnimals();
    }

    @Override
    protected String getEmptyMessage() {
        return "No Animals obtained yet!";
    }

    @Override
    protected void displayTitle() {
        System.out.println("Sell Farm Animals");
    }

    @Override
    protected void displayTable(List<Animal> items) {
        System.out.println("===================================================");
        System.out.printf("| %-3s | %-10s | %-15s | %-10s |\n", "No.", "Type", "Name", "Price");
        System.out.println("===================================================");

        int counter = 1;
        for (Animal animal : items) {
            System.out.printf(
                "| %-3d | %-10s | %-15s | %-10.1f |\n",
                counter++,
                animal.getType(),
                animal.getName(),
                animal.getPrice()
            );
        }
        System.out.println("===================================================");
    }

    @Override
    protected String getItemTypeName() {
        return "Farm Animal";
    }

    @Override
    protected void performSale(int choice, Animal item) {
        if (storeViewModel.sellAnimal(choice)) {
            System.out.println("Sucessfully sold a farm animal!");
        } else {
            System.out.println("Invalid choice!");
        }
    }
}
