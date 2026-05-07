package ui.views;

import java.util.Scanner;

import commands.BuyAnimalCommand;
import commands.SellAnimalCommand;
import commands.SellAnimalProductCommand;
import ui.menus.MenuComposite;
import ui.menus.MenuItem;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class AnimalStoreView {
    private Scanner scanner;
    private ConsoleUtils consoleUtils;

    public AnimalStoreView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public void show(StoreViewModel storeViewModel, PlayerViewModel playerViewModel) {
        MenuComposite menu = new MenuComposite("Animal Shop");
        menu.add(new MenuItem("Buy Farm Animals", new BuyAnimalCommand(storeViewModel, playerViewModel, scanner, consoleUtils)));
        menu.add(new MenuItem("Sell Farm Animals", new SellAnimalCommand(storeViewModel, playerViewModel, scanner, consoleUtils)));
        menu.add(new MenuItem("Sell Animal Products", new SellAnimalProductCommand(storeViewModel, playerViewModel, scanner, consoleUtils)));
        menu.add(new MenuItem("Exit", null));

        while (true) {
            consoleUtils.spaceConsole();
            System.out.printf("Money: %.2f$\n", playerViewModel.getMoney());
            menu.render();

            String input = scanner.nextLine().trim();
            if (input.equals("4")) {
                return;
            }

            menu.handleInput(input);
        }
    }
}
