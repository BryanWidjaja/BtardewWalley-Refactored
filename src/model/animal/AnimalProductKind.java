package model.animal;

public class AnimalProductKind {
    private final String name;
    private final double basePrice;

    public AnimalProductKind(String name, double basePrice) {
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
