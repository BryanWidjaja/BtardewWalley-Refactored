package command.view;

import model.PlayerItem;
import model.item.AnimalProduct;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewAnimalProductsCommand extends ViewInventoryItemsCommand<AnimalProduct> {

    public ViewAnimalProductsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        super(playerViewModel, consoleUtils, AnimalProduct.class);
    }

    @Override
    protected void displayItem(int index, PlayerItem item) {
        AnimalProduct animalProduct = getActualItem(item);
        System.out.printf("%d. %s(%d) - %d\n", index,
                animalProduct.getName(),
                animalProduct.getGrade().getLevel(),
                item.getQuantity());
    }

    @Override
    protected String getEmptyMessage() {
        return "No animal products in inventory!";
    }
}
