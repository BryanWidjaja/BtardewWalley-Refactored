package model.item.animalproduct;

public class AnimalProductDefinition {
    private final String name;
    private final double basePrice;

    public AnimalProductDefinition(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
