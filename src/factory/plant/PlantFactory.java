package factory.plant;

import model.plants.Plant;

public interface PlantFactory {
    Plant createPlant(int x, int y, int growthTime, double price, boolean harvestable);
}