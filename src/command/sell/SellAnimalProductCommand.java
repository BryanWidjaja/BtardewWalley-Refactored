package command.sell;

import java.util.List;
import java.util.Scanner;

import model.item.ItemStack;
import model.item.animalproduct.AnimalProduct;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class SellAnimalProductCommand extends SellItemCommand<ItemStack> {

    public SellAnimalProductCommand(
        StoreViewModel storeViewModel,
        PlayerViewModel playerViewModel,
        Scanner sc,
        ConsoleUtils consoleUtils
    ) {
        super(storeViewModel, playerViewModel, sc, consoleUtils);
    }

    @Override
    protected List<ItemStack> getItems() {
        return playerViewModel.findItemsByType(AnimalProduct.class);
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
    protected void displayTable(List<ItemStack> items) {
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
        for (ItemStack item : items) {
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
    protected void performSale(int choice, ItemStack item) {
        int quantityToSell = promptQuantity(item.getQuantity());
        storeViewModel.sellAnimalProduct(choice, quantityToSell);
        System.out.println("Sucessfully sold an animal product!");
    }

    private int promptQuantity(int maxQuantity) {
        int quantity = -1;
        do {
            System.out.printf("How many items do you want to sell [1-%d]: ", maxQuantity);

            try {
                quantity = sc.nextInt();
                sc.nextLine();
            } catch (Exception exception) {
                sc.nextLine();
            }
        } while (quantity < 1 || quantity > maxQuantity);

        return quantity;
    }
}
