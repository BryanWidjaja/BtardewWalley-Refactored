package model.animal;

import model.Coordinate;
import model.item.animalproduct.AnimalProductDefinition;

public class Sheep extends Animal {
    public Sheep(
        char symbol,
        String name,
        String type,
        AnimalProductDefinition animalProduct,
        HarvestStats harvestStats,
        Coordinate position,
        double price
    ) {
        super(symbol, name, type, animalProduct, harvestStats, position, price);
    }

    @Override
    public String requiredToolName() {
        return "Shears";
    }
}
