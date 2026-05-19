package ui.view;

import command.buy.BuySeedCommand;
import command.sell.SellFarmProductCommand;
import java.util.Scanner;
import ui.menu.MenuComposite;
import ui.menu.MenuLeaf;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class FarmStoreView {
    private final Scanner scanner;
    private final ConsoleUtils consoleUtils;

    public FarmStoreView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public void show(StoreViewModel storeViewModel, PlayerViewModel playerViewModel) {
        MenuComposite menu = new MenuComposite("Farm Shop");
        menu.add(new MenuLeaf(
            "Buy Seeds",
            new BuySeedCommand(
                storeViewModel,
                playerViewModel,
                scanner,
                consoleUtils
            )
        ));
        menu.add(new MenuLeaf(
            "Sell Farm Products",
            new SellFarmProductCommand(
                storeViewModel,
                playerViewModel,
                scanner,
                consoleUtils
            )
        ));
        menu.add(new MenuLeaf("Exit", null));

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
