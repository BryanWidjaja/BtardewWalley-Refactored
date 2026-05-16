package services;

import java.util.List;

import database.DatabaseRegistry;
import model.animal.Animal;
import model.item.PlantSeed;
import model.item.Tool;

public class StoreService {

    public List<Tool> getTools() {
        return DatabaseRegistry.getList(Tool.class);
    }

    public List<PlantSeed> getSeeds() {
        return DatabaseRegistry.getList(PlantSeed.class);
    }

    public List<Animal> getAnimals() {
        return DatabaseRegistry.getList(Animal.class);
    }
}
