package command.view;

import java.util.List;
import model.animal.Animal;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewAnimalsCommand extends ViewItemsCommand<Animal> {

    public ViewAnimalsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        super(playerViewModel, consoleUtils);
    }

    @Override
    protected List<Animal> getItems() {
        return playerViewModel.getAnimals();
    }

    @Override
    protected void displayItem(int index, Animal animal) {
        System.out.printf("%d. %s(%s)\n", index, animal.getName(), animal.getType());
    }

    @Override
    protected String getEmptyMessage() {
        return "No animals in inventory!";
    }
}
