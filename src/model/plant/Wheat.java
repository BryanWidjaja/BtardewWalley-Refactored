package model.plant;

public class Wheat extends Plant {
    public Wheat(int x, int y, int growthTime, double price, boolean harvestable) {
        super('w', "Wheat", x, y, growthTime, price, harvestable);
    }
}