package command.devmode;

import command.Command;
import viewmodel.MapViewModel;

public class TeleportCommand implements Command {
    private final MapViewModel mapViewModel;
    private final int mapIndex;

    public TeleportCommand(MapViewModel mapViewModel, int mapIndex) {
        this.mapViewModel = mapViewModel;
        this.mapIndex = mapIndex;
    }

    @Override
    public void execute() {
        mapViewModel.devModeTeleport(mapIndex);
    }
}
