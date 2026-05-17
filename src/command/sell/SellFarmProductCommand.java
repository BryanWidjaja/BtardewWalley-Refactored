package command.sell;

import java.util.List;
import java.util.Scanner;

import model.item.ItemStack;
import model.item.farmproduct.FarmProduct;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class SellFarmProductCommand extends SellItemCommand<ItemStack> {

    public SellFarmProductCommand(
        StoreViewModel storeViewModel,
        PlayerViewModel playerViewModel,
        Scanner sc,
        ConsoleUtils consoleUtils
    ) {
        super(storeViewModel, playerViewModel, sc, consoleUtils);
    }

    @Override
    protected List<ItemStack> getItems() {
        return playerViewModel.findItemsByType(FarmProduct.class);
    }

    @Override
    protected String getEmptyMessage() {
        return "No Farm Products in inventory!";
    }

    @Override
    protected void displayTitle() {
        System.out.println("Sell Farm Products");
    }

    @Override
    protected void displayTable(List<ItemStack> items) {
        System.out.println("===========================================================");
        System.out.printf(
            "| %-3s | %-10s | %-10s | %-10s | %-10s |\n",
            "No.",
            "Name",
            "Freshness",
            "Quantity",
            "Price"
        );
        System.out.println("===========================================================");

        int counter = 1;
        for (ItemStack item : items) {
            FarmProduct farmProduct = (FarmProduct) item.getItem();
            System.out.printf(
                    "| %-3d | %-10s | %-10d | %-10d | %-10.1f |\n",
                    counter++,
                    farmProduct.getName(),
                    farmProduct.getFreshness().getLevel(),
                    item.getQuantity(),
                    farmProduct.getSellingPrice()
            );
        }
        System.out.println("===========================================================");
    }

    @Override
    protected String getItemTypeName() {
        return "Farm Product";
    }

    @Override
    protected void performSale(int choice, ItemStack item) {
        int quantityToSell = promptQuantity(item.getQuantity());
        storeViewModel.sellFarmProduct(choice, quantityToSell);
        System.out.println("Sucessfully sold a farm product!");
    }

    private int promptQuantity(int maxQuantity) {
        int quantity = -1;
        do {
            System.out.printf("How many items do you want to sell [1-%d]: ", maxQuantity);
            if (sc.hasNextInt()) {
                quantity = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine();
            }
        } while (quantity < 1 || quantity > maxQuantity);
        return quantity;
    }
}
