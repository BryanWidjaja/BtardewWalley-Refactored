package commands;

import models.Animal;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public class ViewAnimalsCommand implements Command {
    private PlayerViewModel playerViewModel;
    private ConsoleUtils consoleUtils;

    public ViewAnimalsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        this.playerViewModel = playerViewModel;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        consoleUtils.spaceConsole();
        int counter = 1;
        for (Animal animal : playerViewModel.getAnimals()) {
            System.out.printf("%d. %s(%s)\n", counter++, animal.getName(), animal.getType());
        }
        if (counter == 1) System.out.println("No animals in inventory!");
        consoleUtils.pause();
    }
}
