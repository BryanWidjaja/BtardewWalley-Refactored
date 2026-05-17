package services.repository;

import java.util.List;

import store.GameCatalog;
import model.animal.Animal;
import model.item.plantseed.PlantSeed;
import model.item.tool.Tool;

public class StoreRepository {

    public List<Tool> getTools() {
        return GameCatalog.getTools();
    }

    public List<PlantSeed> getSeeds() {
        return GameCatalog.getSeeds();
    }

    public List<Animal> getAnimals() {
        return GameCatalog.getAnimals();
    }
}
