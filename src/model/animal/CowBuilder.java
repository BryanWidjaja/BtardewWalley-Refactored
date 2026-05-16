package model.animal;

import model.Coordinate;

public class CowBuilder implements AnimalBuilder {
    private char symbol = 'C';
    private String name;
    private String type = "Cow";
    private AnimalProductKind animalProduct;
    private HarvestStats harvestStats;
    private Coordinate position;
    private double price;

    @Override
    public AnimalBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public AnimalBuilder animalProduct(AnimalProductKind animalProduct) {
        this.animalProduct = animalProduct;
        return this;
    }

    @Override
    public AnimalBuilder harvestStats(HarvestStats harvestStats) {
        this.harvestStats = harvestStats;
        return this;
    }

    @Override
    public AnimalBuilder position(Coordinate position) {
        this.position = position;
        return this;
    }

    @Override
    public AnimalBuilder price(double price) {
        this.price = price;
        return this;
    }

    @Override
    public Animal build() {
        return new Cow(symbol, name, type, animalProduct, harvestStats, position, price);
    }
}
