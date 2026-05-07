package factories;

import models.Animal;
import models.Cow;

public class CowFactory implements AnimalFactory {
    @Override
    public Animal createAnimal(String name, int x, int y) {
        return new Cow(name, 2, x, y, 300.0, true);
    }

    @Override
    public Animal createAnimal(String name, int harvestRate, int x, int y, double price, boolean harvestable) {
        return new Cow(name, harvestRate, x, y, price, harvestable);
    }
}
