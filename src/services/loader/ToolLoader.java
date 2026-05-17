package services.loader;

import store.GameCatalog;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.item.tool.Tool;

public class ToolLoader {
	public static void loadAvailableTools () {
		try {
			GameCatalog.getTools().clear();
			LineLoader toolLoader = parts -> {
				String name = parts[0].trim();
				int price = Integer.parseInt(parts[1].trim());
				GameCatalog.getTools().add(new Tool(name, price));
			};
			toolLoader.loadFile("system_data/tools.txt");
	    } catch (FileNotFoundException exception) {
	        System.out.println("tools.txt not found");
	    } catch (IOException exception) {
	        System.out.println("Error loading tools!");
	    }
	}
}
