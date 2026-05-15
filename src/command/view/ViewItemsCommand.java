package command.view;

import java.util.List;

import command.Command;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public abstract class ViewItemsCommand<T> implements Command {
    protected final PlayerViewModel playerViewModel;
    protected final ConsoleUtils consoleUtils;

    public ViewItemsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        this.playerViewModel = playerViewModel;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        consoleUtils.spaceConsole();
        List<T> items = getItems();
        int counter = 1;
        for (T item : items) {
            displayItem(counter++, item);
        }
        if (counter == 1) {
            System.out.println(getEmptyMessage());
        }
        consoleUtils.pause();
    }

    protected abstract List<T> getItems();
    protected abstract void displayItem(int index, T item);
    protected abstract String getEmptyMessage();
}
