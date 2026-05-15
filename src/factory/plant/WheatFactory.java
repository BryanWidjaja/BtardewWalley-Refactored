package factory.plant;

import model.plants.Plant;
import model.plants.Wheat;

public class WheatFactory implements PlantFactory {
    @Override
    public Plant createPlant(int x, int y, int growthTime, double price, boolean harvestable) {
        return new Wheat(x, y, growthTime, price, harvestable);
    }
}