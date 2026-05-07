package commands;

import models.PlayerItem;
import models.items.Tool;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewToolsCommand implements Command {
    private PlayerViewModel playerViewModel;
    private ConsoleUtils consoleUtils;

    public ViewToolsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        this.playerViewModel = playerViewModel;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        consoleUtils.spaceConsole();
        int counter = 1;
        for (PlayerItem item : playerViewModel.getInventory()) {
            if (item.getItem() instanceof Tool) {
                System.out.printf("%d. %s\n", counter++, item.getItem().getName());
            }
        }
        if (counter == 1) System.out.println("No tools in inventory!");
        consoleUtils.pause();
    }
}
