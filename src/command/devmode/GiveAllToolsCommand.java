package command.devmode;

import command.Command;
import database.DatabaseRegistry;
import model.item.Tool;
import viewmodel.PlayerViewModel;

public class GiveAllToolsCommand implements Command {
    private final PlayerViewModel playerViewModel;

    public GiveAllToolsCommand(PlayerViewModel playerViewModel) {
        this.playerViewModel = playerViewModel;
    }

    @Override
    public void execute() {
        for (Tool tool : DatabaseRegistry.getList(Tool.class)) {
            playerViewModel.addItem(new Tool(tool.getName(), (int) tool.getPrice()), 1);
        }
    }
}
