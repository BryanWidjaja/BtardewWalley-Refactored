package factory.animal;

import model.Coordinate;
import model.animal.Animal;
import model.animal.AnimalDirector;
import model.animal.AnimalProductKind;
import model.animal.CowBuilder;
import model.animal.HarvestStats;

public class CowFactory implements AnimalFactory {
    private final AnimalDirector director = new AnimalDirector();

    @Override
    public Animal createAnimal(String name, AnimalProductKind animalProduct, int harvestRate, int defaultHarvestRate,
            int x, int y, double price, boolean harvestable) {
        CowBuilder builder = new CowBuilder();
        director.build(builder, name, animalProduct,
                       new HarvestStats(harvestRate, defaultHarvestRate, harvestable),
                       new Coordinate(x, y), price);
        return builder.build();
    }
}
