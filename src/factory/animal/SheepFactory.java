package factory.animal;

import model.Coordinate;
import model.animal.Animal;
import model.animal.AnimalDirector;
import model.animal.HarvestStats;
import model.animal.SheepBuilder;

public class SheepFactory implements AnimalFactory {
    private final AnimalDirector director = new AnimalDirector();

    @Override
    public Animal createAnimal(String name, String animalProduct, int harvestRate, int defaultHarvestRate,
            int x, int y, double price, boolean harvestable) {
        SheepBuilder builder = new SheepBuilder();
        director.buildSheep(builder, name, animalProduct, 
                           new HarvestStats(harvestRate, defaultHarvestRate, harvestable), 
                           new Coordinate(x, y), price);
        return builder.build();
    }
}
