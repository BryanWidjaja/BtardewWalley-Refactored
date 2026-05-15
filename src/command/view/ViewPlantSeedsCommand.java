package command.view;

import model.PlayerItem;
import model.item.PlantSeed;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewPlantSeedsCommand extends ViewInventoryItemsCommand<PlantSeed> {

    public ViewPlantSeedsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        super(playerViewModel, consoleUtils, PlantSeed.class);
    }

    @Override
    protected void displayItem(int index, PlayerItem item) {
        System.out.printf("%d. %s - %d\n", index,
                item.getItem().getName(), item.getQuantity());
    }

    @Override
    protected String getEmptyMessage() {
        return "No plant seeds in inventory!";
    }
}
