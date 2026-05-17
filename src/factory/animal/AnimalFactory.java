package factory.animal;

import model.animal.Animal;
import model.item.animalproduct.AnimalProductDefinition;

public interface AnimalFactory {
    Animal createAnimal(
        String name,
        AnimalProductDefinition animalProduct,
        int harvestRate,
        int defaultHarvestRate,
        int x,
        int y,
        double price,
        boolean harvestable
    );
}
