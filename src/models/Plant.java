package models;

public class Plant {
	private char symbol;
	private String name;
	private Coordinate position;
	private int growthTime;
	private double price;
	private boolean harvestable;

	public Plant(char symbol, String name, int plantX, int plantY, int growthTime, double price, boolean harvestable) {
		this.symbol = symbol;
		this.name = name;
		this.position = new Coordinate(plantX, plantY);
		this.growthTime = growthTime;
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
		return growthTime;
	}

	public double getPrice() {
		return price;
	}

	public boolean isHarvestable() {
		return harvestable;
	}

	public boolean tickGrowth() {
		if (harvestable) return false;

		growthTime--;

		if (growthTime == 0) {
			harvestable = true;
			return true;
		}

		return false;
	}
}