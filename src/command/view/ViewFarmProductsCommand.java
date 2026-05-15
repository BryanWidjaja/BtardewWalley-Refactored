package command.view;

import model.PlayerItem;
import model.item.FarmProduct;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewFarmProductsCommand extends ViewInventoryItemsCommand<FarmProduct> {

    public ViewFarmProductsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        super(playerViewModel, consoleUtils, FarmProduct.class);
    }

    @Override
    protected void displayItem(int index, PlayerItem item) {
        FarmProduct fp = getActualItem(item);
        System.out.printf("%d. %s(%d) - %d\n", index,
                fp.getName(),
                fp.getFreshness().getLevel(),
                item.getQuantity());
    }

    @Override
    protected String getEmptyMessage() {
        return "No farm products in inventory!";
    }
}
