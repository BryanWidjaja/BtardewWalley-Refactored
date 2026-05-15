package ui.view;

import java.util.Scanner;

import command.buy.BuyAnimalCommand;
import command.sell.SellAnimalCommand;
import command.sell.SellAnimalProductCommand;
import ui.menu.MenuComposite;
import ui.menu.MenuItem;
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
            menu.render(() -> System.out.printf("Money: %.2f$\n", playerViewModel.getMoney()));

            String input = scanner.nextLine().trim();
            if (input.equals("4")) {
                return;
            }

            menu.handleInput(input);
        }
    }
}
