package models.items;

public class FarmProduct extends Item {
	private int freshness;

	public FarmProduct(String name, double price, int freshness) {
		super(name, price);
		this.freshness = freshness;
	}

	public int getFreshness() {
		return freshness;
	}

	public boolean tickFreshness() {
		freshness--;
		return freshness <= 0;
	}

	@Override
	public String toString() {
		return String.format("%s (Freshness: %d) - $%.0f", getName(), freshness, getPrice());
	}
}
