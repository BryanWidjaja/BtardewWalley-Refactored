package command.buy;

import java.util.List;
import java.util.Scanner;

import model.animal.Animal;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class BuyAnimalCommand extends BuyItemCommand<Animal> {

    public BuyAnimalCommand(StoreViewModel storeViewModel, PlayerViewModel playerViewModel, Scanner sc, ConsoleUtils consoleUtils) {
        super(storeViewModel, playerViewModel, sc, consoleUtils);
    }

    @Override
    protected List<Animal> getItems() {
        return storeViewModel.getAvailableAnimals();
    }

    @Override
    protected void displayTitle() {
        System.out.println("Buy Farm Animals");
    }

    @Override
    protected void displayTable(List<Animal> items) {
        System.out.println("================================================");
        System.out.printf("| %-3s | %-10s | %-12s | %-10s |\n", "No.", "Name", "Harvest Rate", "Price");
        System.out.println("================================================");

        int counter = 1;
        for (Animal animal : items) {
            System.out.printf("| %-3d | %-10s | %-12d | %-10.1f |\n",
                    counter++, animal.getType(), animal.getHarvestRate(), animal.getPrice());
        }
        System.out.println("================================================");
    }

    @Override
    protected String getItemTypeName() {
        return "Farm Animals";
    }

    @Override
    protected Animal getItem(int choice) {
        return storeViewModel.getAnimal(choice);
    }

    @Override
    protected void performPurchase(int choice, Animal animal) {
        if (playerViewModel.getMoney() >= animal.getPrice()) {
            String name = getAnimalName();
            if (storeViewModel.buyAnimal(choice, name)) {
                System.out.println("Successfully bought a farm animal");
            }
        } else {
            System.out.println("Not enough money!");
        }
    }

    private String getAnimalName() {
        while (true) {
            System.out.print("Input new farm animal's name [<= 15 characters]: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                continue;
            }
            if (name.length() > 15) {
                System.out.println("Name must be 15 characters or fewer!");
                continue;
            }
            if (playerViewModel.isAnimalNameTaken(name)) {
                System.out.println("Name already taken!");
                continue;
            }
            return name;
        }
    }
}
