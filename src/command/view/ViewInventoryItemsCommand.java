package command.view;

import java.util.List;
import model.PlayerItem;
import model.item.Item;
import util.ConsoleUtils;
import viewmodel.PlayerViewModel;

public abstract class ViewInventoryItemsCommand<T extends Item> extends ViewItemsCommand<PlayerItem> {
    private final Class<T> type;

    public ViewInventoryItemsCommand(PlayerViewModel playerViewModel, ConsoleUtils consoleUtils, Class<T> type) {
        super(playerViewModel, consoleUtils);
        this.type = type;
    }

    @Override
    protected List<PlayerItem> getItems() {
        return playerViewModel.findItemsByType(type);
    }
    
    @SuppressWarnings("unchecked")
    protected T getActualItem(PlayerItem playerItem) {
        return (T) playerItem.getItem();
    }
}
