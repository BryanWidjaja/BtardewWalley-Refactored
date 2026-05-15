package model.animal;

import model.Coordinate;

public abstract class Animal {
	protected char symbol;
	protected String name;
	protected String type;
	protected String animalProduct;
	protected HarvestStats harvestStats;
	protected Coordinate position;
	protected double price;

	public Animal(char symbol, String name, String type, String animalProduct,
			HarvestStats harvestStats, Coordinate position, double price) {
		this.symbol = symbol;
		this.name = name;
		this.type = type;
		this.animalProduct = animalProduct;
		this.harvestStats = harvestStats;
		this.position = position;
		this.price = price;
	}

	public char getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getAnimalProduct() {
		return animalProduct;
	}

	public int getHarvestRate() {
		return harvestStats.getCurrentRate();
	}

	public int getDefaultHarvestRate() {
		return harvestStats.getDefaultRate();
	}

	public Coordinate getPosition() {
		return position;
	}

	public double getPrice() {
		return price;
	}

	public boolean isHarvestable() {
		return harvestStats.isHarvestable();
	}

	public void tickHarvest() {
		harvestStats.tick();
	}

	public void collectProduct() {
		harvestStats.collect();
	}
}
