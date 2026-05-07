package commands;

import models.PlayerItem;
import models.items.FarmProduct;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewFarmProductsCommand implements Command {
    private PlayerViewModel playerViewModel;
    private ConsoleUtils consoleUtils;

    public ViewFarmProductsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        this.playerViewModel = playerViewModel;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        consoleUtils.spaceConsole();
        int counter = 1;
        for (PlayerItem item : playerViewModel.getInventory()) {
            if (item.getItem() instanceof FarmProduct) {
                FarmProduct fp = (FarmProduct) item.getItem();
                System.out.printf("%d. %s(%d) - %d\n", counter++,
                        fp.getName(),
                        fp.getFreshness(),
                        item.getQuantity());
            }
        }
        if (counter == 1) System.out.println("No farm products in inventory!");
        consoleUtils.pause();
    }
}
