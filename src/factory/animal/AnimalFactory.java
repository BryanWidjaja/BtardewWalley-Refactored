package factory.animal;

import model.animal.Animal;
import model.animal.AnimalProductKind;

public interface AnimalFactory {
    Animal createAnimal(String name, AnimalProductKind animalProduct, int harvestRate, int defaultHarvestRate,
            int x, int y, double price, boolean harvestable);
}
