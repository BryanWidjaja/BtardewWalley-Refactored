package commands;

import models.PlayerItem;
import models.items.PlantSeed;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewPlantSeedsCommand implements Command {
    private PlayerViewModel playerViewModel;
    private ConsoleUtils consoleUtils;

    public ViewPlantSeedsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        this.playerViewModel = playerViewModel;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        consoleUtils.spaceConsole();
        int counter = 1;
        for (PlayerItem item : playerViewModel.getInventory()) {
            if (item.getItem() instanceof PlantSeed) {
                System.out.printf("%d. %s - %d\n", counter++,
                        item.getItem().getName(), item.getQuantity());
            }
        }
        if (counter == 1) System.out.println("No plant seeds in inventory!");
        consoleUtils.pause();
    }
}
