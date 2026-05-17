package model.animal;

import model.Coordinate;
import model.item.animalproduct.AnimalProductDefinition;

public interface AnimalBuilder {
    AnimalBuilder name(String name);
    AnimalBuilder animalProduct(AnimalProductDefinition animalProduct);
    AnimalBuilder harvestStats(HarvestStats harvestStats);
    AnimalBuilder position(Coordinate position);
    AnimalBuilder price(double price);
    Animal build();
}
