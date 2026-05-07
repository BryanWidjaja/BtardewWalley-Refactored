package factories;

import models.Animal;
import models.Sheep;

public class SheepFactory implements AnimalFactory {
    @Override
    public Animal createAnimal(String name, int x, int y) {
        return new Sheep(name, 5, x, y, 500.0, true);
    }

    @Override
    public Animal createAnimal(String name, int harvestRate, int x, int y, double price, boolean harvestable) {
        return new Sheep(name, harvestRate, x, y, price, harvestable);
    }
}
