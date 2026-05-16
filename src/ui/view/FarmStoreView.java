package ui.view;

import java.util.Scanner;

import command.buy.BuySeedCommand;
import command.sell.SellFarmProductCommand;
import ui.menu.MenuComposite;
import ui.menu.MenuItem;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class FarmStoreView {
    private Scanner scanner;
    private ConsoleUtils consoleUtils;

    public FarmStoreView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public void show(StoreViewModel storeViewModel, PlayerViewModel playerViewModel) {
        MenuComposite menu = new MenuComposite("Farm Shop");
        menu.add(new MenuItem("Buy Seeds", new BuySeedCommand(storeViewModel, playerViewModel, scanner, consoleUtils)));
        menu.add(new MenuItem("Sell Farm Products", new SellFarmProductCommand(storeViewModel, playerViewModel, scanner, consoleUtils)));
        menu.add(new MenuItem("Exit", null));

        while (true) {
            consoleUtils.spaceConsole();
            menu.render(() -> System.out.printf("Money: %.2f$\n", playerViewModel.getMoney()));

            String input = scanner.nextLine().trim();
            if (input.equals(String.valueOf(menu.exitOption()))) {
                return;
            }

            menu.handleInput(input);
        }
    }
}
