package command.view;

import model.item.ItemStack;
import model.item.tool.Tool;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewToolsCommand extends ViewTypedInventoryCommand<Tool> {

    public ViewToolsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        super(playerViewModel, consoleUtils, Tool.class);
    }

    @Override
    protected void displayItem(int index, ItemStack item) {
        System.out.printf("%d. %s\n", index, item.getItem().getName());
    }

    @Override
    protected String getEmptyMessage() {
        return "No tools in inventory!";
    }
}
