package commands;

import ui.views.ToolStoreView;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class OpenToolStoreCommand implements Command {
    private ToolStoreView view;
    private StoreViewModel storeViewModel;
    private PlayerViewModel playerViewModel;

    public OpenToolStoreCommand(ToolStoreView view, StoreViewModel storeViewModel, PlayerViewModel playerViewModel) {
        this.view = view;
        this.storeViewModel = storeViewModel;
        this.playerViewModel = playerViewModel;
    }

    @Override
    public void execute() {
        view.show(storeViewModel, playerViewModel);
    }
}
