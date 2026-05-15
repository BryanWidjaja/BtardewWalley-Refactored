package services.loader;

import database.DatabaseRegistry;
import factory.animal.AnimalFactoryProvider;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import model.animal.Animal;
import model.item.AnimalProduct;
import util.FileLineReader;

public class AnimalLoader {
	public static void loadAvailableAnimals () {
		try {
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

				Animal animal = AnimalFactoryProvider.getFactory(type)
						.createAnimal("", productName, harvestRate, harvestRate, 0, 0, price, false);
				DatabaseRegistry.getList(Animal.class).add(animal);

				DatabaseRegistry.<String, Double>getMap(AnimalProduct.class).put(productName, productPrice);
			}
		} catch (FileNotFoundException e) {
			System.out.println("animal data file not found");
		} catch (IOException e) {
			System.out.println("Error loading animals!");
		}
	}
}
