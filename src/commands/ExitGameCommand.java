package commands;

import viewmodel.MainViewModel;

public class ExitGameCommand implements Command {
    private MainViewModel mainViewModel;

    public ExitGameCommand(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void execute() {
        mainViewModel.shutdown();
    }
}
