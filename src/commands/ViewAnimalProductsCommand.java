package commands;

import models.PlayerItem;
import models.items.AnimalProduct;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewAnimalProductsCommand implements Command {
    private PlayerViewModel playerViewModel;
    private ConsoleUtils consoleUtils;

    public ViewAnimalProductsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        this.playerViewModel = playerViewModel;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        consoleUtils.spaceConsole();
        int counter = 1;
        for (PlayerItem item : playerViewModel.getInventory()) {
            if (item.getItem() instanceof AnimalProduct) {
                AnimalProduct ap = (AnimalProduct) item.getItem();
                System.out.printf("%d. %s(%d) - %d\n", counter++,
                        ap.getName(),
                        ap.getGrade(),
                        item.getQuantity());
            }
        }
        if (counter == 1) System.out.println("No animal products in inventory!");
        consoleUtils.pause();
    }
}
