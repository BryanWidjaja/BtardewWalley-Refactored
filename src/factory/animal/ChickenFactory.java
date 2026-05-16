package factory.animal;

import model.Coordinate;
import model.animal.Animal;
import model.animal.AnimalDirector;
import model.animal.AnimalProductKind;
import model.animal.ChickenBuilder;
import model.animal.HarvestStats;

public class ChickenFactory implements AnimalFactory {
    private final AnimalDirector director = new AnimalDirector();

    @Override
    public Animal createAnimal(String name, AnimalProductKind animalProduct, int harvestRate, int defaultHarvestRate,
            int x, int y, double price, boolean harvestable) {
        ChickenBuilder builder = new ChickenBuilder();
        director.build(builder, name, animalProduct,
                       new HarvestStats(harvestRate, defaultHarvestRate, harvestable),
                       new Coordinate(x, y), price);
        return builder.build();
    }
}
