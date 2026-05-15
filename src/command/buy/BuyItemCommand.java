package command.buy;

import java.util.List;
import java.util.Scanner;

import command.Command;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public abstract class BuyItemCommand<T> implements Command {
    protected final StoreViewModel storeViewModel;
    protected final PlayerViewModel playerViewModel;
    protected final Scanner sc;
    protected final ConsoleUtils consoleUtils;

    public BuyItemCommand(StoreViewModel storeViewModel, PlayerViewModel playerViewModel, Scanner sc, ConsoleUtils consoleUtils) {
        this.storeViewModel = storeViewModel;
        this.playerViewModel = playerViewModel;
        this.sc = sc;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        while (true) {
            List<T> items = getItems();
            if (items.isEmpty() && handleEmptyItems()) {
                return;
            }

            consoleUtils.spaceConsole();
            displayTitle();
            System.out.println();
            System.out.println("Money: $" + playerViewModel.getMoney());
            System.out.println();

            displayTable(items);

            int choice = getUserSelection(items.size());
            if (choice == 0) return;
            if (choice == -2) continue;

            if (choice != -1) {
                T selectedItem = getItem(choice);
                performPurchase(choice, selectedItem);
            } else {
                System.out.println("Invalid choice!");
            }
            
            consoleUtils.pause();
        }
    }

    protected abstract List<T> getItems();
    protected abstract void displayTitle();
    protected abstract void displayTable(List<T> items);
    protected abstract String getItemTypeName();
    protected abstract T getItem(int choice);
    protected abstract void performPurchase(int choice, T item);

    protected boolean handleEmptyItems() {
        return false;
    }

    protected int getUserSelection(int maxChoice) {
        System.out.print("Choose " + getItemTypeName() + " [" + (maxChoice > 0 ? "1-" + maxChoice : "0") + "] [0 to exit]: ");
        if (!sc.hasNextInt()) {
            sc.nextLine();
            return -2;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 0) return 0;
        if (choice >= 1 && choice <= maxChoice) return choice;
        return -1;
    }
}
