package commands;

import ui.views.AnimalStoreView;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class OpenAnimalStoreCommand implements Command {
    private AnimalStoreView view;
    private StoreViewModel storeViewModel;
    private PlayerViewModel playerViewModel;

    public OpenAnimalStoreCommand(AnimalStoreView view, StoreViewModel storeViewModel, PlayerViewModel playerViewModel) {
        this.view = view;
        this.storeViewModel = storeViewModel;
        this.playerViewModel = playerViewModel;
    }

    @Override
    public void execute() {
        view.show(storeViewModel, playerViewModel);
    }
}
