package models;

public class Cow extends Animal {
    public Cow(String name, int harvestRate, int x, int y, double price, boolean harvestable) {
        super('C', name, "Cow", "Milk", harvestRate, x, y, price, harvestable);
    }

    @Override
    public int getDefaultHarvestRate() {
        return 2;
    }
}
