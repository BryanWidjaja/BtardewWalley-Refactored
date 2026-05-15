package factory.animal;

import model.animal.Animal;

public interface AnimalFactory {
    Animal createAnimal(String name, String animalProduct, int harvestRate, int defaultHarvestRate,
            int x, int y, double price, boolean harvestable);
}
