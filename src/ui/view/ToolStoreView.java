package ui.view;

import java.util.Scanner;

import command.buy.BuyToolCommand;
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
        new BuyToolCommand(storeViewModel, playerViewModel, scanner, consoleUtils).execute();
    }
}
