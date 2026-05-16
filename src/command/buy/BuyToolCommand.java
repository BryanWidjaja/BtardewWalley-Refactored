package command.buy;

import java.util.List;
import java.util.Scanner;

import model.item.Tool;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class BuyToolCommand extends BuyItemCommand<Tool> {

    public BuyToolCommand(
        StoreViewModel storeViewModel,
        PlayerViewModel playerViewModel,
        Scanner sc,
        ConsoleUtils consoleUtils
    ) {
        super(storeViewModel, playerViewModel, sc, consoleUtils);
    }

    @Override
    protected List<Tool> getItems() {
        return storeViewModel.getAvailableTools();
    }

    @Override
    protected void displayTitle() {
        System.out.println("Buy Tools");
    }

    @Override
    protected void displayTable(List<Tool> items) {
        System.out.println("=================================");
        System.out.printf("| %-3s | %-10s | %-10s |\n", "No.", "Name", "Price");
        System.out.println("=================================");

        int counter = 1;
        for (Tool tool : items) {
            System.out.printf("| %-3s | %-10s | %-10s |\n", counter++, tool.getName(), tool.getPrice());
        }
        System.out.println("=================================");
    }

    @Override
    protected String getItemTypeName() {
        return "Tool";
    }

    @Override
    protected Tool getItem(int choice) {
        return storeViewModel.getTool(choice);
    }

    @Override
    protected void performPurchase(int choice, Tool tool) {
        if (storeViewModel.buyTool(choice)) {
            System.out.println("Successfully buy a tool");
        } else {
            System.out.println("Not enough money!");
        }
    }

    @Override
    protected boolean handleEmptyItems() {
        System.out.println("You already buy all tools!");
        consoleUtils.pause();
        return true;
    }
}
