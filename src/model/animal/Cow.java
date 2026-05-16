package model.animal;

import model.Coordinate;

public class Cow extends Animal {
    public Cow(char symbol, String name, String type, AnimalProductKind animalProduct,
              HarvestStats harvestStats, Coordinate position, double price) {
        super(symbol, name, type, animalProduct, harvestStats, position, price);
    }

    @Override
    public String requiredToolName() {
        return "Bucket";
    }
}
