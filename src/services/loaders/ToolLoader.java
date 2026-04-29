package services.loaders;

import database.ToolDatabase;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import models.items.Tool;

public class ToolLoader {
	public static void loadAvailableTools () {
		try {
	    	FileReader file = new FileReader("tools.txt");
			BufferedReader br = new BufferedReader(file);
			
			String line;
			
            while ((line = br.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String name = parts[0].trim();
            	
            	int price = Integer.parseInt(parts[1].trim());
            	
            	ToolDatabase.getDatabase().getTools().add(new Tool(name, price));
            }
            
            br.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("tools.txt not found");
	    } catch (IOException e) {
	        System.out.println("Error loading tools!");
	    }
	}
}
