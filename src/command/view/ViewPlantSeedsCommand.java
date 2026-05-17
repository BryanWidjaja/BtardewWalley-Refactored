package command.view;

import model.item.ItemStack;
import model.item.plantseed.PlantSeed;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewPlantSeedsCommand extends ViewTypedInventoryCommand<PlantSeed> {

    public ViewPlantSeedsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        super(playerViewModel, consoleUtils, PlantSeed.class);
    }

    @Override
    protected void displayItem(int index, ItemStack item) {
        System.out.printf(
            "%d. %s - %d\n",
            index,
            item.getItem().getName(),
            item.getQuantity()
        );
    }

    @Override
    protected String getEmptyMessage() {
        return "No plant seeds in inventory!";
    }
}
