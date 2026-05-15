package model.item;

public class Tool extends Item {
    public Tool(String name, int price) {
    	super(name, price);
    }

    @Override
    public String toString() {
    	return String.format("%s (Tool) - $%.0f", getName(), getPrice());
    }
}
