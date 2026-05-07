package commands;

import ui.views.SleepView;
import viewmodel.MapViewModel;

public class SleepCommand implements Command {
    private SleepView view;
    private MapViewModel mapViewModel;

    public SleepCommand(SleepView view, MapViewModel mapViewModel) {
        this.view = view;
        this.mapViewModel = mapViewModel;
    }

    @Override
    public void execute() {
        if (view.showSleepPrompt()) {
            mapViewModel.sleep();
        }
    }
}
