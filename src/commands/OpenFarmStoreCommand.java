package commands;

import ui.views.FarmStoreView;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class OpenFarmStoreCommand implements Command {
    private FarmStoreView view;
    private StoreViewModel storeViewModel;
    private PlayerViewModel playerViewModel;

    public OpenFarmStoreCommand(FarmStoreView view, StoreViewModel storeViewModel, PlayerViewModel playerViewModel) {
        this.view = view;
        this.storeViewModel = storeViewModel;
        this.playerViewModel = playerViewModel;
    }

    @Override
    public void execute() {
        view.show(storeViewModel, playerViewModel);
    }
}
