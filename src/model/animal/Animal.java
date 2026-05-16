package model.animal;

import java.util.Locale;

import model.Coordinate;

public abstract class Animal {
	protected char symbol;
	protected String name;
	protected String type;
	protected AnimalProductKind animalProduct;
	protected HarvestStats harvestStats;
	protected Coordinate position;
	protected double price;

	public Animal(
		char symbol,
		String name,
		String type,
		AnimalProductKind animalProduct,
		HarvestStats harvestStats,
		Coordinate position,
		double price
	) {
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

	public AnimalProductKind getAnimalProduct() {
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

	public abstract String requiredToolName();

	public String toSaveLine() {
		return String.format(
			Locale.ROOT,
			"ANIMAL#%c#%s#%s#%s#%d#%d#%d#%.2f#%b",
			symbol,
			name,
			type,
			animalProduct.getName(),
			getHarvestRate(),
			position.getX(),
			position.getY(),
			price,
			isHarvestable()
		);
	}
}
