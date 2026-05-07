package factories;

import models.Animal;

public interface AnimalFactory {
    Animal createAnimal(String name, int x, int y);
    Animal createAnimal(String name, int harvestRate, int x, int y, double price, boolean harvestable);
}
