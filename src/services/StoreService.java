package services;

import java.util.List;

import databases.AnimalDatabase;
import databases.SeedDatabase;
import databases.ToolDatabase;
import models.Animal;
import models.items.PlantSeed;
import models.items.Tool;

public class StoreService {

    public List<Tool> getTools() {
        return ToolDatabase.getDatabase().getTools();
    }

    public List<PlantSeed> getSeeds() {
        return SeedDatabase.getDatabase().getPlantSeeds();
    }

    public List<Animal> getAnimals() {
        return AnimalDatabase.getDatabase().getAnimals();
    }

    public Tool findTool(String name) {
        for (Tool tool : getTools()) {
            if (tool.getName().equals(name)) {
                return tool;
            }
        }
        return null;
    }

    public void removeTool(Tool tool) {
        getTools().remove(tool);
    }

    public PlantSeed findSeed(String name) {
        for (PlantSeed seed : getSeeds()) {
            if (seed.getName().equals(name)) {
                return seed;
            }
        }
        return null;
    }

    public Animal findAnimalTemplate(String type) {
        for (Animal animal : getAnimals()) {
            if (animal.getType().equals(type)) {
                return animal;
            }
        }
        return null;
    }
}
