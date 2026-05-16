package model.plants;

import java.util.Locale;

import model.Coordinate;

public abstract class Plant {
	private char symbol;
	private String name;
	private Coordinate position;
	private GrowthDuration growth;
	private double price;
	private boolean harvestable;

	public Plant(char symbol, String name, int plantX, int plantY, int growthTime, double price, boolean harvestable) {
		this.symbol = symbol;
		this.name = name;
		this.position = new Coordinate(plantX, plantY);
		this.growth = new GrowthDuration(growthTime);
		this.price = price;
		this.harvestable = harvestable;
	}

	public char getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public Coordinate getPosition() {
		return position;
	}

	public int getGrowthTime() {
		return growth.getDaysRemaining();
	}

	public double getPrice() {
		return price;
	}

	public boolean isHarvestable() {
		return harvestable;
	}

	public boolean tickGrowth() {
		if (harvestable) {
			return false;
		}
		if (growth.tick()) {
			harvestable = true;
			return true;
		}
		return false;
	}

	public String toSaveLine() {
		return String.format(Locale.ROOT, "PLANT#%c#%s#%d#%d#%d#%.2f#%b",
				symbol, name, position.getX(), position.getY(),
				getGrowthTime(), price, harvestable);
	}
}
