package command.sell;

import java.util.List;
import java.util.Scanner;

import command.Command;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public abstract class SellItemCommand<T> implements Command {
    protected final StoreViewModel storeViewModel;
    protected final PlayerViewModel playerViewModel;
    protected final Scanner sc;
    protected final ConsoleUtils consoleUtils;

    public SellItemCommand(StoreViewModel storeViewModel, PlayerViewModel playerViewModel, Scanner sc, ConsoleUtils consoleUtils) {
        this.storeViewModel = storeViewModel;
        this.playerViewModel = playerViewModel;
        this.sc = sc;
        this.consoleUtils = consoleUtils;
    }

    @Override
    public void execute() {
        while (true) {
            List<T> items = getItems();
            if (items.isEmpty()) {
                System.out.println(getEmptyMessage());
                consoleUtils.pause();
                return;
            }

            consoleUtils.spaceConsole();
            displayTitle();
            System.out.println();
            System.out.println("Money: $" + playerViewModel.getMoney());
            System.out.println();

            displayTable(items);

            int choice = getUserSelection(items.size());
            if (choice == 0) {
                return;
            }
            if (choice == -2) {
                continue;
            }

            if (choice != -1) {
                T item = items.get(choice - 1);
                performSale(choice, item);
            } else {
                System.out.println("Invalid choice!");
            }
            consoleUtils.pause();
        }
    }

    protected abstract List<T> getItems();
    protected abstract String getEmptyMessage();
    protected abstract void displayTitle();
    protected abstract void displayTable(List<T> items);
    protected abstract String getItemTypeName();
    protected abstract void performSale(int choice, T item);

    protected int getUserSelection(int maxChoice) {
        System.out.print("Choose " + getItemTypeName() + " to sell [1-" + maxChoice + "] [0 to exit]: ");
        if (!sc.hasNextInt()) {
            sc.nextLine();
            return -2;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 0) {
            return 0;
        }
        if (choice >= 1 && choice <= maxChoice) {
            return choice;
        }
        return -1;
    }
}
