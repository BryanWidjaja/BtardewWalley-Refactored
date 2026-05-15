package factory.plant;

import java.util.HashMap;
import java.util.Map;

public class PlantFactoryProvider {
    private static Map<String, PlantFactory> factories = new HashMap<>();

    static {
        factories.put("Wheat", new WheatFactory());
        factories.put("Beetroot", new BeetrootFactory());
    }

    public static PlantFactory getFactory(String type) {
        PlantFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unknown plant type: " + type);
        }
        return factory;
    }
}