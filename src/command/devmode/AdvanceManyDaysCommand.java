package command.devmode;

import command.Command;
import viewmodel.MapViewModel;

public class AdvanceManyDaysCommand implements Command {
    private static final int DAYS_TO_ADVANCE = 20;

    private final MapViewModel mapViewModel;

    public AdvanceManyDaysCommand(MapViewModel mapViewModel) {
        this.mapViewModel = mapViewModel;
    }

    @Override
    public void execute() {
        for (int i = 0; i < DAYS_TO_ADVANCE; i++) {
            mapViewModel.sleep();
        }
    }
}
