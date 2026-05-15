package model.animal;

import model.Coordinate;

public class SheepBuilder implements AnimalBuilder {
    private char symbol = 'S';
    private String name;
    private String type = "Sheep";
    private String animalProduct;
    private HarvestStats harvestStats;
    private Coordinate position;
    private double price;

    @Override public AnimalBuilder name(String name) { this.name = name; return this; }
    @Override public AnimalBuilder animalProduct(String animalProduct) { this.animalProduct = animalProduct; return this; }
    @Override public AnimalBuilder harvestStats(HarvestStats harvestStats) { this.harvestStats = harvestStats; return this; }
    @Override public AnimalBuilder position(Coordinate position) { this.position = position; return this; }
    @Override public AnimalBuilder price(double price) { this.price = price; return this; }

    @Override
    public Animal build() {
        return new Sheep(symbol, name, type, animalProduct, harvestStats, position, price);
    }
}
