package model.item;

import java.util.Locale;

public class FarmProduct extends Item {
	private FarmProductFreshness freshness;

    public FarmProduct(String name, double price, int freshnessLevel) {
    	super(name, price);
    	this.freshness = FarmProductFreshness.fromLevel(freshnessLevel);
    }

	public FarmProductFreshness getFreshness() {
		return freshness;
	}

	public void setFreshness(FarmProductFreshness freshness) {
		this.freshness = freshness;
	}

	public double getSellingPrice() {
		return getPrice() * freshness.getMultiplier() * 2;
	}

	public boolean tickFreshness() {
		this.freshness = freshness.getNextState();
		return freshness.isExpired();
	}

	public boolean isExpired() {
		return freshness.isExpired();
	}

	@Override
	public boolean canStackWith(Item other) {
		if (!super.canStackWith(other)) {
			return false;
		}
		return this.freshness == ((FarmProduct) other).getFreshness();
	}

	@Override
	public String toString() {
		return String.format("%s(%d)", getName(), freshness.getLevel());
	}

	@Override
	public String toSaveLine(int quantity) {
		return String.format(
			Locale.ROOT,
			"FARM_PRODUCT#%s#%.2f#%d#%d",
			getName(),
			getPrice(),
			freshness.getLevel(),
			quantity
		);
	}
}
