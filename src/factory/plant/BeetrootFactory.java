package factory.plant;

import model.plants.Beetroot;
import model.plants.Plant;

public class BeetrootFactory implements PlantFactory {
    @Override
    public Plant createPlant(int x, int y, int growthTime, double price, boolean harvestable) {
        return new Beetroot(x, y, growthTime, price, harvestable);
    }
}