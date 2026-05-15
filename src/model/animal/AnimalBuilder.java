package model.animal;

import model.Coordinate;

public interface AnimalBuilder {
    AnimalBuilder name(String name);
    AnimalBuilder animalProduct(String animalProduct);
    AnimalBuilder harvestStats(HarvestStats harvestStats);
    AnimalBuilder position(Coordinate position);
    AnimalBuilder price(double price);
    Animal build();
}
