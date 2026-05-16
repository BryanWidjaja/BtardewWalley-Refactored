package command.sell;

import java.util.List;
import java.util.Scanner;
import model.PlayerItem;
import model.item.Item;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public abstract class SellProductCommand<T extends Item> extends SellItemCommand<PlayerItem> {
    private final Class<T> productType;

    public SellProductCommand(
        StoreViewModel storeViewModel,
        PlayerViewModel playerViewModel,
        Scanner sc,
        ConsoleUtils consoleUtils,
        Class<T> productType
    ) {
        super(storeViewModel, playerViewModel, sc, consoleUtils);
        this.productType = productType;
    }

    @Override
    protected List<PlayerItem> getItems() {
        return playerViewModel.findItemsByType(productType);
    }

    @Override
    protected void performSale(int choice, PlayerItem item) {
        int quantityToSell = getQuantityToSell(item.getQuantity());
        executeSale(choice, quantityToSell);
        System.out.println("Sucessfully sold a " + getProductTypeName() + "!");
    }

    protected abstract void executeSale(int choice, int quantity);
    protected abstract String getProductTypeName();

    private int getQuantityToSell(int maxQuantity) {
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
