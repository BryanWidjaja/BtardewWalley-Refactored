package command;

import ui.view.SleepView;
import viewmodel.MapViewModel;

public class ConfirmSleepCommand implements Command {
    private final SleepView sleepView;
    private final MapViewModel mapViewModel;

    public ConfirmSleepCommand(SleepView sleepView, MapViewModel mapViewModel) {
        this.sleepView = sleepView;
        this.mapViewModel = mapViewModel;
    }

    @Override
    public void execute() {
        if (sleepView.showSleepPrompt()) {
            mapViewModel.sleep();
        }
    }
}
