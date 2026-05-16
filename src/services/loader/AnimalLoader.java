package services.loader;

import database.DatabaseRegistry;
import factory.animal.AnimalFactoryProvider;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import model.animal.Animal;
import model.animal.AnimalProductKind;
import util.FileLineReader;

public class AnimalLoader {
	public static void loadAvailableAnimals () {
		try {
			DatabaseRegistry.getList(Animal.class).clear();
			DatabaseRegistry.getList(AnimalProductKind.class).clear();

			List<String[]> animalRows = FileLineReader.readRows("system_data/animals.txt");
			List<String[]> productRows = FileLineReader.readRows("system_data/animal_products.txt");

			for (int i = 0; i < animalRows.size(); i++) {
				String[] animalParts = animalRows.get(i);
				String[] productParts = productRows.get(i);

				String type = animalParts[0].trim();
				int harvestRate = Integer.parseInt(animalParts[1].trim());
				double price = Double.parseDouble(animalParts[2].trim());

				String productName = productParts[0].trim();
				double productPrice = Double.parseDouble(productParts[1].trim());

				AnimalProductKind productKind = new AnimalProductKind(productName, productPrice);
				DatabaseRegistry.getList(AnimalProductKind.class).add(productKind);

				Animal animal = AnimalFactoryProvider.getFactory(type)
						.createAnimal("", productKind, harvestRate, harvestRate, 0, 0, price, false);
				DatabaseRegistry.getList(Animal.class).add(animal);
			}
		} catch (FileNotFoundException exception) {
			System.out.println("animal data file not found");
		} catch (IOException exception) {
			System.out.println("Error loading animals!");
		}
	}
}
