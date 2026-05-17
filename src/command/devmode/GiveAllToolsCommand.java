package command.devmode;

import command.Command;
import model.item.tool.Tool;
import store.GameCatalog;
import viewmodel.PlayerViewModel;

public class GiveAllToolsCommand implements Command {
    private final PlayerViewModel playerViewModel;

    public GiveAllToolsCommand(PlayerViewModel playerViewModel) {
        this.playerViewModel = playerViewModel;
    }

    @Override
    public void execute() {
        for (Tool tool : GameCatalog.getTools()) {
            playerViewModel.addItem(new Tool(tool.getName(), (int) tool.getPrice()), 1);
        }
    }
}
