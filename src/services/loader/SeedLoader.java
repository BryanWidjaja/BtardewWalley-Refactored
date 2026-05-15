package services.loader;

import database.DatabaseRegistry;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.item.PlantSeed;

public class SeedLoader {
	public static void loadAvailableSeeds () {
		try {
			LineLoader seedLoader = parts -> {
				String name = parts[0].trim();
				int growthTime = Integer.parseInt(parts[1].trim());
				double price = Double.parseDouble(parts[2].trim());
				DatabaseRegistry.getList(PlantSeed.class).add(
						new PlantSeed(name, price, Character.toLowerCase(name.charAt(0)), growthTime));
			};
			seedLoader.loadFile("system_data/plants.txt");
	    } catch (FileNotFoundException e) {
	        System.out.println("plants.txt not found");
	    } catch (IOException e) {
	        System.out.println("Error loading seeds!");
	    }
	}
}
