package iterators;

import java.util.List;
import models.PlayerItem;

public class InventoryIterator implements Iterator<PlayerItem> {
    private List<PlayerItem> items;
    private int position = 0;

    public InventoryIterator(List<PlayerItem> items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return position < items.size();
    }

    @Override
    public PlayerItem getNext() {
        if (!hasNext()) {
            return null;
        }
        return items.get(position++);
    }

    @Override
    public void remove() {
        if (position <= 0) {
            return;
        }
        items.remove(--position);
    }
}
