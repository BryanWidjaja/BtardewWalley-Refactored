package command.view;

import model.PlayerItem;
import model.item.Tool;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewToolsCommand extends ViewInventoryItemsCommand<Tool> {

    public ViewToolsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        super(playerViewModel, consoleUtils, Tool.class);
    }

    @Override
    protected void displayItem(int index, PlayerItem item) {
        System.out.printf("%d. %s\n", index, item.getItem().getName());
    }

    @Override
    protected String getEmptyMessage() {
        return "No tools in inventory!";
    }
}
