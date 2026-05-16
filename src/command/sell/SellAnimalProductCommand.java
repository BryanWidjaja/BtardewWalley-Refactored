package command.sell;

import java.util.List;
import java.util.Scanner;

import model.PlayerItem;
import model.item.AnimalProduct;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class SellAnimalProductCommand extends SellProductCommand<AnimalProduct> {

    public SellAnimalProductCommand(
        StoreViewModel storeViewModel,
        PlayerViewModel playerViewModel,
        Scanner sc,
        ConsoleUtils consoleUtils
    ) {
        super(storeViewModel, playerViewModel, sc, consoleUtils, AnimalProduct.class);
    }

    @Override
    protected String getEmptyMessage() {
        return "No Animal Products in inventory!";
    }

    @Override
    protected void displayTitle() {
        System.out.println("Sell Animal Products");
    }

    @Override
    protected void displayTable(List<PlayerItem> items) {
        System.out.println("===========================================================");
        System.out.printf(
            "| %-3s | %-10s | %-10s | %-10s | %-10s |\n",
            "No.",
            "Name",
            "Grade",
            "Quantity",
            "Price"
        );
        System.out.println("===========================================================");

        int counter = 1;
        for (PlayerItem item : items) {
            AnimalProduct animalProduct = (AnimalProduct) item.getItem();
            System.out.printf(
                    "| %-3d | %-10s | %-10s | %-10d | %-10.1f |\n",
                    counter++,
                    animalProduct.getName(),
                    animalProduct.getGrade().name(),
                    item.getQuantity(),
                    animalProduct.getSellingPrice()
            );
        }
        System.out.println("===========================================================");
    }

    @Override
    protected String getItemTypeName() {
        return "Animal Product";
    }

    @Override
    protected String getProductTypeName() {
        return "animal product";
    }

    @Override
    protected void executeSale(int choice, int quantity) {
        storeViewModel.sellAnimalProduct(choice, quantity);
    }
}
