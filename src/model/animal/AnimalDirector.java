package model.animal;

import model.Coordinate;

public class AnimalDirector {
    public void build(
        AnimalBuilder builder,
        String name,
        AnimalProductKind animalProduct,
        HarvestStats harvestStats,
        Coordinate position,
        double price
    ) {
        builder.name(name)
               .animalProduct(animalProduct)
               .harvestStats(harvestStats)
               .position(position)
               .price(price);
    }
}
