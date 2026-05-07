package ui.views;

import java.util.Scanner;
import ui.menus.MenuComposite;
import ui.menus.MenuItem;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import commands.*;

public class InventoryView {
    private Scanner scanner;
    private ConsoleUtils consoleUtils;

    public InventoryView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public void show(PlayerViewModel playerViewModel) {
        MenuComposite menu = new MenuComposite("Inventory Menu");
        menu.add(new MenuItem("View Animal Products", new ViewAnimalProductsCommand(playerViewModel, consoleUtils)));
        menu.add(new MenuItem("View Farm Products", new ViewFarmProductsCommand(playerViewModel, consoleUtils)));
        menu.add(new MenuItem("View Animals", new ViewAnimalsCommand(playerViewModel, consoleUtils)));
        menu.add(new MenuItem("View Tools", new ViewToolsCommand(playerViewModel, consoleUtils)));
        menu.add(new MenuItem("View Plant Seeds", new ViewPlantSeedsCommand(playerViewModel, consoleUtils)));
        menu.add(new MenuItem("Exit", null));

        while (true) {
            consoleUtils.spaceConsole();
            menu.render();

            String input = scanner.nextLine().trim();
            if (input.equals("6")) {
                return;
            }

            menu.handleInput(input);
        }
    }
}
