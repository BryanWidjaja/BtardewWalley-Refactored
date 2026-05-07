package models;

public class Chicken extends Animal {
    public Chicken(String name, int harvestRate, int x, int y, double price, boolean harvestable) {
        super('c', name, "Chicken", "Egg", harvestRate, x, y, price, harvestable);
    }

    @Override
    public int getDefaultHarvestRate() {
        return 1;
    }
}
