package command.sell;

import java.util.List;
import java.util.Scanner;

import model.PlayerItem;
import model.item.FarmProduct;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class SellFarmProductCommand extends SellProductCommand<FarmProduct> {

    public SellFarmProductCommand(
        StoreViewModel storeViewModel,
        PlayerViewModel playerViewModel,
        Scanner sc,
        ConsoleUtils consoleUtils
    ) {
        super(storeViewModel, playerViewModel, sc, consoleUtils, FarmProduct.class);
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
    protected void displayTable(List<PlayerItem> items) {
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
        for (PlayerItem item : items) {
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
    protected String getProductTypeName() {
        return "farm product";
    }

    @Override
    protected void executeSale(int choice, int quantity) {
        storeViewModel.sellFarmProduct(choice, quantity);
    }
}
