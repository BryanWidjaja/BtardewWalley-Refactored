package models;

public abstract class Animal {
	protected char symbol;
	protected String name;
	protected String type;
	protected String animalProduct;
	protected int harvestRate;
	protected Coordinate position;
	protected double price;
	protected boolean harvestable;

	public Animal(char symbol, String name, String type, String animalProduct, int harvestRate,
			int animalX, int animalY, double price, boolean harvestable) {
		this.symbol = symbol;
		this.name = name;
		this.type = type;
		this.animalProduct = animalProduct;
		this.harvestRate = harvestRate;
		this.position = new Coordinate(animalX, animalY);
		this.price = price;
		this.harvestable = harvestable;
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
		return harvestRate;
	}

	public Coordinate getPosition() {
		return position;
	}

	public double getPrice() {
		return price;
	}

	public boolean isHarvestable() {
		return harvestable;
	}

	public abstract int getDefaultHarvestRate();

	public void tickHarvest() {
		if (harvestable) return;

		harvestRate--;

		if (harvestRate == 0) {
			harvestable = true;
			harvestRate = getDefaultHarvestRate();
		}
	}

	public void collectProduct() {
		harvestable = false;
	}
}
