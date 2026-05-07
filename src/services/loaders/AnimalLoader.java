package services.loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import databases.AnimalDatabase;
import models.Animal;
import factories.AnimalFactoryProvider;

public class AnimalLoader {
	public static void loadAvailableAnimals () {
		try {
	    	FileReader fileReader = new FileReader("animals.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			
            while ((line = bufferedReader.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String type = parts[0].trim();
            	
            	int harvestRate = Integer.parseInt(parts[1].trim());
            	
            	double price = Double.parseDouble(parts[2].trim());
            	
                Animal animal = AnimalFactoryProvider.getFactory(type).createAnimal("", harvestRate, 0, 0, price, false);
                AnimalDatabase.getDatabase().getAnimals().add(animal);
            }
            
            bufferedReader.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("animals.txt not found");
	    } catch (IOException e) {
	        System.out.println("Error loading animals!");
	    }
	}
}
