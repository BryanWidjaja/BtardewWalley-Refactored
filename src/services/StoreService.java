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

    public Tool findTool(String name) {
        return DatabaseRegistry.findOne(Tool.class, tool -> tool.getName().equals(name));
    }

    public void removeTool(Tool tool) {
        getTools().remove(tool);
    }

    public PlantSeed findSeed(String name) {
        return DatabaseRegistry.findOne(PlantSeed.class, seed -> seed.getName().equals(name));
    }

    public Animal findAnimalTemplate(String type) {
        return DatabaseRegistry.findOne(Animal.class, animal -> animal.getType().equals(type));
    }
}
