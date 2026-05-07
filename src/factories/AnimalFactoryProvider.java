package factories;

import java.util.HashMap;
import java.util.Map;

public class AnimalFactoryProvider {
    private static Map<String, AnimalFactory> factories = new HashMap<>();

    static {
        factories.put("Chicken", new ChickenFactory());
        factories.put("Cow", new CowFactory());
        factories.put("Sheep", new SheepFactory());
    }

    public static AnimalFactory getFactory(String type) {
        AnimalFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unknown animal type: " + type);
        }
        return factory;
    }
}
