package ui.view;

import java.util.Scanner;

import command.buy.BuyToolCommand;
import ui.menu.MenuComposite;
import ui.menu.MenuItem;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class ToolStoreView {
    private Scanner scanner;
    private ConsoleUtils consoleUtils;

    public ToolStoreView(Scanner scanner, ConsoleUtils consoleUtils) {
        this.scanner = scanner;
        this.consoleUtils = consoleUtils;
    }

    public void show(StoreViewModel storeViewModel, PlayerViewModel playerViewModel) {
        MenuComposite menu = new MenuComposite("Tool Shop");
        menu.add(new MenuItem("Buy Tools", new BuyToolCommand(storeViewModel, playerViewModel, scanner, consoleUtils)));
        menu.add(new MenuItem("Exit", null));

        while (true) {
            consoleUtils.spaceConsole();
            System.out.println("Money: $" + playerViewModel.getMoney());
            menu.render();

            String input = scanner.nextLine().trim();
            if (input.equals("2")) {
                return;
            }

            menu.handleInput(input);
        }
    }
}
