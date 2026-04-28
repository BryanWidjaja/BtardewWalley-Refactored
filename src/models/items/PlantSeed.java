package models.items;

public class PlantSeed extends Item {
	
	private final char symbol;
	private final int growthTime;
	
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
}
