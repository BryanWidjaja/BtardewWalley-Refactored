package commands;

import ui.views.PlantView;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class PlantSeedCommand implements Command {
    private PlantView view;
    private PlayerViewModel playerViewModel;
    private MapViewModel mapViewModel;

    public PlantSeedCommand(PlantView view, PlayerViewModel playerViewModel, MapViewModel mapViewModel) {
        this.view = view;
        this.playerViewModel = playerViewModel;
        this.mapViewModel = mapViewModel;
    }

    @Override
    public void execute() {
        view.showPlantPrompt(playerViewModel, mapViewModel);
    }
}
