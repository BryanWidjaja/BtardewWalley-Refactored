package model.item.plantseed;

import java.util.Locale;

import model.item.Item;

public class PlantSeed extends Item {
	private char symbol;
	private int growthTime;

	public PlantSeed(String name, double price, char symbol, int growthTime) {
		super(name, price);
		this.symbol = symbol;
		this.growthTime = growthTime;
	}

	public char getSymbol() {
		return symbol;
	}

	public int getGrowthTime() {
		return growthTime;
	}

	@Override
	public String toString() {
		return String.format("%s Seed [%c] (Growth: %d days) - $%.0f", getName(), symbol, growthTime, getPrice());
	}

	@Override
	public String toSaveLine(int quantity) {
		return String.format(
			Locale.ROOT,
			"SEED#%s#%.2f#%c#%d#%d",
			getName(),
			getPrice(),
			symbol,
			growthTime,
			quantity
		);
	}
}
