package factory.animal;

import model.Coordinate;
import model.animal.Animal;
import model.animal.HarvestStats;
import model.animal.SheepBuilder;
import model.item.animalproduct.AnimalProductDefinition;

public class SheepFactory implements AnimalFactory {
    @Override
    public Animal createAnimal(
        String name,
        AnimalProductDefinition animalProduct,
        int harvestRate,
        int defaultHarvestRate,
        int x,
        int y,
        double price,
        boolean harvestable
    ) {
        return new SheepBuilder()
            .name(name)
            .animalProduct(animalProduct)
            .harvestStats(new HarvestStats(harvestRate, defaultHarvestRate, harvestable))
            .position(new Coordinate(x, y))
            .price(price)
            .build();
    }
}
