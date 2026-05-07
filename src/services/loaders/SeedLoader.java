package services.loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import databases.SeedDatabase;
import models.items.PlantSeed;

public class SeedLoader {
	public static void loadAvailableSeeds () {
		try {
	    	FileReader fileReader = new FileReader("plants.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			
            while ((line = bufferedReader.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String name = parts[0].trim();
            	
            	int growthTime = Integer.parseInt(parts[1].trim());
            	
            	double price = Double.parseDouble(parts[2].trim());
            	
                SeedDatabase.getDatabase().getPlantSeeds().add(new PlantSeed(name, price, Character.toLowerCase(name.charAt(0)), growthTime));
            }
            
            bufferedReader.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("plants.txt not found");
	    } catch (IOException e) {
	        System.out.println("Error loading seeds!");
	    }
	}
}
