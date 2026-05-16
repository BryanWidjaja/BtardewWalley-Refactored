package model.item;

import java.util.Locale;

public class Tool extends Item {
    public Tool(String name, int price) {
    	super(name, price);
    }

    @Override
    public String toString() {
    	return String.format("%s (Tool) - $%.0f", getName(), getPrice());
    }

    @Override
    public String toSaveLine(int quantity) {
        return String.format(Locale.ROOT, "TOOL#%s#%d", getName(), quantity);
    }
}
