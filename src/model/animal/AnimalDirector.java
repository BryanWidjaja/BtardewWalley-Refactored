package model.animal;

import model.Coordinate;

public class AnimalDirector {
    public void buildChicken(AnimalBuilder builder, String name, String animalProduct, 
                            HarvestStats harvestStats, Coordinate position, double price) {
        builder.name(name)
               .animalProduct(animalProduct)
               .harvestStats(harvestStats)
               .position(position)
               .price(price);
    }

    public void buildCow(AnimalBuilder builder, String name, String animalProduct, 
                        HarvestStats harvestStats, Coordinate position, double price) {
        builder.name(name)
               .animalProduct(animalProduct)
               .harvestStats(harvestStats)
               .position(position)
               .price(price);
    }

    public void buildSheep(AnimalBuilder builder, String name, String animalProduct, 
                           HarvestStats harvestStats, Coordinate position, double price) {
        builder.name(name)
               .animalProduct(animalProduct)
               .harvestStats(harvestStats)
               .position(position)
               .price(price);
    }
}
