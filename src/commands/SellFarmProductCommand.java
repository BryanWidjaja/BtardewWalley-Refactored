package commands;

import java.util.Scanner;
import models.PlayerItem;
import models.items.FarmProduct;
import util.ConsoleUtils;
import util.FreshnessUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class SellFarmProductCommand implements Command {
    private StoreViewModel storeViewModel;
    private PlayerViewModel playerViewModel;
    private Scanner sc;
    private ConsoleUtils consoleUtils;

    public SellFarmProductCommand(StoreViewModel storeViewModel, PlayerViewModel playerViewModel, Scanner sc, ConsoleUtils consoleUtils) {
        this.storeViewModel = storeViewModel;
        this.playerViewModel = playerViewModel;
        this.sc = sc;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        while (true) {
            boolean hasProducts = false;
            for (PlayerItem item : playerViewModel.getInventory()) {
                if (item.getItem() instanceof FarmProduct) {
                    hasProducts = true;
                    break;
                }
            }

            if (!hasProducts) {
                System.out.println("No Farm Products in inventory!");
                consoleUtils.pause();
                return;
            }

            int counter = 1;
            consoleUtils.spaceConsole();

            System.out.println("Sell Farm Products");
            System.out.println();
            System.out.println("Money: $" + playerViewModel.getMoney());
            System.out.println();

            System.out.println("===========================================================");
            System.out.printf("| %-3s | %-10s | %-10s | %-10s | %-10s |\n",
                    "No.", "Name", "Grade", "Quantity", "Price");
            System.out.println("===========================================================");

            for (PlayerItem item : playerViewModel.getInventory()) {
                if (item.getItem() instanceof FarmProduct) {
                    FarmProduct fp = (FarmProduct) item.getItem();
                    System.out.printf(
                            "| %-3d | %-10s | %-10d | %-10d | %-10.1f |\n",
                            counter++, 
                            fp.getName(), 
                            fp.getFreshness(), 
                            item.getQuantity(),
                            (double) (fp.getPrice() * FreshnessUtils.getFreshnessMultiplier(fp.getFreshness()) * 2)
                    );
                }
            }

            System.out.println("===========================================================");
            System.out.print("Choose Farm Product to sell [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                    int selectedIndex = playerViewModel.findFarmProductIndex(choice);
                    PlayerItem selectedItem = playerViewModel.getInventory().get(selectedIndex);

                    int quantityToSell = -1;
                    do {
                        System.out.printf("How many items do you want to sell [%d-%d]: ",
                                1, selectedItem.getQuantity());
                        try {
                            quantityToSell = sc.nextInt();
                            sc.nextLine();
                        } catch (Exception e) {
                            sc.nextLine();
                        }
                    } while (quantityToSell < 1 || quantityToSell > selectedItem.getQuantity());

                    storeViewModel.sellFarmProduct(choice, quantityToSell);
                    System.out.println("Sucessfully sold a farm product!");
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
