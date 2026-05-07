package databases;

import java.util.ArrayList;
import models.Animal;

public class AnimalDatabase {
	private static AnimalDatabase database;
	private ArrayList<Animal> animals;
	
	private AnimalDatabase () {
		animals = new ArrayList<>();
	}
	
	public static AnimalDatabase getDatabase () {
		if (database == null)
			database = new AnimalDatabase();

		return database;
	}

	public ArrayList<Animal> getAnimals() {
		return animals;
	}
}
