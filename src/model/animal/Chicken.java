package model.animal;

import model.Coordinate;

public class Chicken extends Animal {
    public Chicken(char symbol, String name, String type, AnimalProductKind animalProduct,
                  HarvestStats harvestStats, Coordinate position, double price) {
        super(symbol, name, type, animalProduct, harvestStats, position, price);
    }

    @Override
    public String requiredToolName() {
        return null;
    }
}
