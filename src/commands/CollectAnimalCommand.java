package commands;

import ui.views.AnimalHarvestView;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class CollectAnimalCommand implements Command {
    private AnimalHarvestView view;
    private PlayerViewModel playerViewModel;
    private MapViewModel mapViewModel;

    public CollectAnimalCommand(AnimalHarvestView view, PlayerViewModel playerViewModel, MapViewModel mapViewModel) {
        this.view = view;
        this.playerViewModel = playerViewModel;
        this.mapViewModel = mapViewModel;
    }

    @Override
    public void execute() {
        view.showCollectAnimal(playerViewModel, mapViewModel);
    }
}
