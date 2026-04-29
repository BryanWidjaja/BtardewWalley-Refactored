package services.loaders;

import database.AnimalDatabase;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import models.Animal;

public class AnimalLoader {
	public static void loadAvailableAnimals () {
		try {
	    	FileReader file = new FileReader("animals.txt");
			BufferedReader br = new BufferedReader(file);
			
			String line;
			
            while ((line = br.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String type = parts[0].trim();
            	
            	int harvestRate = Integer.parseInt(parts[1].trim());
            	
            	double price = Double.parseDouble(parts[2].trim());
            	
                AnimalDatabase.getDatabase().getAnimals().add(new Animal(' ', "", type, "", harvestRate, 0, 0, price, false));
            }
            
            br.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("animals.txt not found");
	    } catch (IOException e) {
	        System.out.println("Error loading animals!");
	    }
	}
}
