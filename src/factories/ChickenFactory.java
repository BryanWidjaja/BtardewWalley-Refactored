package factories;

import models.Animal;
import models.Chicken;

public class ChickenFactory implements AnimalFactory {
    @Override
    public Animal createAnimal(String name, int x, int y) {
        return new Chicken(name, 1, x, y, 200.0, true);
    }

    @Override
    public Animal createAnimal(String name, int harvestRate, int x, int y, double price, boolean harvestable) {
        return new Chicken(name, harvestRate, x, y, price, harvestable);
    }
}
