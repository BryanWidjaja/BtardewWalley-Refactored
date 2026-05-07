package models;

public class Sheep extends Animal {
    public Sheep(String name, int harvestRate, int x, int y, double price, boolean harvestable) {
        super('S', name, "Sheep", "Wool", harvestRate, x, y, price, harvestable);
    }

    @Override
    public int getDefaultHarvestRate() {
        return 5;
    }
}
