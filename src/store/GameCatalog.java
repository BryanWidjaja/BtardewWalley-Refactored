package store;

import java.util.ArrayList;
import java.util.List;

import model.animal.Animal;
import model.item.animalproduct.AnimalProductDefinition;
import model.item.plantseed.PlantSeed;
import model.item.tool.Tool;

public class GameCatalog {
    private static final List<Tool> tools = new ArrayList<>();
    private static final List<PlantSeed> seeds = new ArrayList<>();
    private static final List<Animal> animals = new ArrayList<>();
    private static final List<AnimalProductDefinition> animalProducts = new ArrayList<>();

    private GameCatalog() {
    }

    public static List<Tool> getTools() {
        return tools;
    }

    public static List<PlantSeed> getSeeds() {
        return seeds;
    }

    public static List<Animal> getAnimals() {
        return animals;
    }

    public static List<AnimalProductDefinition> getAnimalProducts() {
        return animalProducts;
    }
}
