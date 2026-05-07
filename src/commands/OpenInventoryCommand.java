package commands;

import ui.views.InventoryView;
import viewmodel.PlayerViewModel;

public class OpenInventoryCommand implements Command {
    private InventoryView view;
    private PlayerViewModel playerViewModel;

    public OpenInventoryCommand(InventoryView view, PlayerViewModel playerViewModel) {
        this.view = view;
        this.playerViewModel = playerViewModel;
    }

    @Override
    public void execute() {
        view.show(playerViewModel);
    }
}
