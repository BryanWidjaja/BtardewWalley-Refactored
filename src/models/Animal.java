package models;

public class Animal {
	private final char symbol;
	private final String name;
	private final String type;
	private final String animalProduct;
	private int harvestRate;
	private final Coordinate position;
	private final double price;
	private boolean harvestable;
	
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

	public void tickHarvest() {
		if (harvestable) return;

		harvestRate--;

		if (harvestRate == 0) {
			harvestable = true;

			if (symbol == 'c') 
				harvestRate = 1;
			else if (symbol == 'C') 
				harvestRate = 2;
			else if (symbol == 'S') 
				harvestRate = 5;
		}
	}

	public void collectProduct() {
		harvestable = false;
	}
}
