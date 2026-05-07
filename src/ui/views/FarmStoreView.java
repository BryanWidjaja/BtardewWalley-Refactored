package ui.views;

import java.util.Scanner;

import commands.BuySeedCommand;
import commands.SellFarmProductCommand;
import ui.menus.MenuComposite;
import ui.menus.MenuItem;
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
            System.out.printf("Money: %.2f$\n", playerViewModel.getMoney());
            menu.render();

            String input = scanner.nextLine().trim();
            if (input.equals("3")) {
                return;
            }

            menu.handleInput(input);
        }
    }
}
