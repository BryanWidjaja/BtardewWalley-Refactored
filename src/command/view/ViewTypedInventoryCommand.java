package command.view;

import java.util.List;
import model.item.ItemStack;
import model.item.Item;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public abstract class ViewTypedInventoryCommand<T extends Item> extends ViewItemsCommand<ItemStack> {
    private final Class<T> type;

    public ViewTypedInventoryCommand(
        PlayerViewModel playerViewModel,
        ConsoleUtils consoleUtils,
        Class<T> type
    ) {
        super(playerViewModel, consoleUtils);
        this.type = type;
    }

    @Override
    protected List<ItemStack> getItems() {
        return playerViewModel.findItemsByType(type);
    }
    
    @SuppressWarnings("unchecked")
    protected T getActualItem(ItemStack playerItem) {
        return (T) playerItem.getItem();
    }
}
