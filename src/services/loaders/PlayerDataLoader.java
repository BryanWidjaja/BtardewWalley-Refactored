package services.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import databases.ToolDatabase;
import iterators.Iterator;
import iterators.ToolIterator;
import models.Animal;
import models.Plant;
import models.Player;
import models.PlayerItem;
import models.User;
import models.items.AnimalProduct;
import models.items.FarmProduct;
import models.items.PlantSeed;
import models.items.Tool;
import ui.views.GameMapView;
import factories.AnimalFactoryProvider;

public class PlayerDataLoader {
	private Player player;
	private User currentUser;
    private Map<String, LineLoader> loaders;

	public PlayerDataLoader(Player player, User currentUser) {
		super();
		this.player = player;
		this.currentUser = currentUser;
	}

	public void loadPlayerData () {
		File saveFile = new File("user_data/" + currentUser.getUsername() + "_data.txt");
		if (!saveFile.exists()) return;
		
        initLoaders();

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(saveFile));
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split("#");
				String type = parts[0];
				
                LineLoader loader = loaders.get(type);
                if (loader != null) {
                    loader.load(parts);
                }
			}
			
			bufferedReader.close();
			
			Iterator<Tool> iterator = new ToolIterator(ToolDatabase.getDatabase().getTools());
			while (iterator.hasNext()) {
				Tool tool = iterator.getNext();
				for (PlayerItem item : player.getInventory()) {
					if (item.getItem() instanceof Tool && item.getItem().getName().equals(tool.getName())) {
						iterator.remove();
						break;
					}
				}
			}
			
		} catch (FileNotFoundException exception) {

		} catch (IOException exception) {
			System.out.println("Error loading player data!");
		}
	}

    private void initLoaders() {
        loaders = new HashMap<>();
        
        loaders.put("DEViewModelODE", parts -> {
            if (parts.length > 1) {
                currentUser.setDevMode(Boolean.parseBoolean(parts[1]));
            }
        });
        
        loaders.put("PLAYER", parts -> {
            player.setMoney(Double.parseDouble(parts[1]));
            player.setDay(Integer.parseInt(parts[2]));
            player.setCurrMapIndex(Integer.parseInt(parts[3]));
            player.getPosition().moveTo(
                Integer.parseInt(parts[4]),
                Integer.parseInt(parts[5])
            );
            player.setCurrTile(parts[6].charAt(0));
        });
        
        loaders.put("TOOL", parts -> {
            String toolName = parts[1];
            int toolQuantity = Integer.parseInt(parts[2]);
            player.getInventory().add(new PlayerItem(
                    new Tool(toolName, 0), toolQuantity));
        });
        
        loaders.put("SEED", parts -> {
            String seedName = parts[1];
            double seedPrice = Double.parseDouble(parts[2]);
            char seedSymbol = parts[3].charAt(0);
            int seedGrowthTime = Integer.parseInt(parts[4]);
            int seedQuantity = Integer.parseInt(parts[5]);
            player.getInventory().add(new PlayerItem(
                    new PlantSeed(seedName, seedPrice, seedSymbol, seedGrowthTime), seedQuantity));
        });
        
        loaders.put("ANIMAL_PRODUCT", parts -> {
            String animalProductName = parts[1];
            double animalProductPrice = Double.parseDouble(parts[2]);
            int animalProductGrade = Integer.parseInt(parts[3]);
            int animalProductQuantity = Integer.parseInt(parts[4]);
            player.getInventory().add(new PlayerItem(
                    new AnimalProduct(animalProductName, (int) animalProductPrice, animalProductGrade), animalProductQuantity));
        });
        
        loaders.put("FARM_PRODUCT", parts -> {
            String farmProductName = parts[1];
            double farmProductPrice = Double.parseDouble(parts[2]);
            int farmProductFreshness = Integer.parseInt(parts[3]);
            int farmProductQuantity = Integer.parseInt(parts[4]);
            player.getInventory().add(new PlayerItem(
                    new FarmProduct(farmProductName, farmProductPrice, farmProductFreshness), farmProductQuantity));
        });
        
        loaders.put("ANIMAL", parts -> {
            char animalSymbol = parts[1].charAt(0);
            String animalName = parts[2];
            String animalType = parts[3];
            int animalHarvestRate = Integer.parseInt(parts[5]);
            int animalX = Integer.parseInt(parts[6]);
            int animalY = Integer.parseInt(parts[7]);
            double animalPrice = Double.parseDouble(parts[8]);
            boolean animalHarvestable = Boolean.parseBoolean(parts[9]);
            Animal animal = AnimalFactoryProvider.getFactory(animalType)
                    .createAnimal(animalName, animalHarvestRate, animalX, animalY, animalPrice, animalHarvestable);
            player.getAnimals().add(animal);
            GameMapView.ANIMAL_FARM_MAP[animalX][animalY] = animalSymbol;
        });
        
        loaders.put("PLANT", parts -> {
            char plantSymbol = parts[1].charAt(0);
            String plantName = parts[2];
            int plantX = Integer.parseInt(parts[3]);
            int plantY = Integer.parseInt(parts[4]);
            int plantGrowthTime = Integer.parseInt(parts[5]);
            double plantPrice = Double.parseDouble(parts[6]);
            boolean plantHarvestable = Boolean.parseBoolean(parts[7]);
            Plant plant = new Plant(plantSymbol, plantName, plantX, plantY, plantGrowthTime, plantPrice, plantHarvestable);
            player.getPlants().add(plant);
            if (plantHarvestable) {
                GameMapView.PLANT_FARM_MAP[plantX][plantY] = Character.toUpperCase(plantSymbol);
            } else {
                GameMapView.PLANT_FARM_MAP[plantX][plantY] = plantSymbol;
            }
        });
    }
}
