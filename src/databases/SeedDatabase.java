package databases;

import java.util.ArrayList;
import models.items.PlantSeed;

public class SeedDatabase {
	private static SeedDatabase database;
	private ArrayList<PlantSeed> plantSeeds;
	
	private SeedDatabase () {
		plantSeeds = new ArrayList<>();
	}
	
	public static SeedDatabase getDatabase () {
		if (database == null)
			database = new SeedDatabase();

		return database;
	}

	public ArrayList<PlantSeed> getPlantSeeds() {
		return plantSeeds;
	}
}
