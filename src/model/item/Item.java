package model.item;

public abstract class Item {
	private String name;
	private double price;

	public Item(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public boolean canStackWith(Item other) {
		return this.getClass().equals(other.getClass()) && this.name.equals(other.getName());
	}

	public abstract String toSaveLine(int quantity);
}
