package services.loader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import store.GameCatalog;
import factory.animal.AnimalFactoryProvider;
import factory.plant.PlantFactoryProvider;
import model.item.ItemStack;
import model.User;
import model.animal.Animal;
import model.item.animalproduct.AnimalProduct;
import model.item.animalproduct.AnimalProductDefinition;
import model.item.animalproduct.AnimalProductGrade;
import model.item.farmproduct.FarmProduct;
import model.item.plantseed.PlantSeed;
import model.item.tool.Tool;
import model.plant.Plant;
import model.player.Player;
import model.map.MapBoardState;

public class PlayerDataLoader {
	private static final String DATA_DIR = "user_data";

	private final Player player;
	private final User currentUser;
	private Map<String, LineLoader> loaders;

	public PlayerDataLoader(Player player, User currentUser) {
		this.player = player;
		this.currentUser = currentUser;
	}

	public void loadPlayerData() {
		File saveFile = new File(DATA_DIR + "/" + currentUser.getUsername() + "_data.txt");
		if (!saveFile.exists()) {
			return;
		}

		initLoaders();
		try {
			LineLoader dispatcher = parts -> {
				LineLoader handler = loaders.get(parts[0]);
				if (handler != null) {
					handler.load(parts);
				}
			};
			dispatcher.loadFile(saveFile.getPath());
		} catch (IOException exception) {
			System.out.println("Error loading player data!");
		} catch (RuntimeException exception) {
			System.out.println("Save file is corrupted or malformed!");
		}
	}

	private void initLoaders() {
		loaders = new HashMap<>();
		loaders.put("DEVMODE", this::loadDevModeLine);
		loaders.put("PLAYER", this::loadPlayerLine);
		loaders.put("TOOL", this::loadToolLine);
		loaders.put("SEED", this::loadSeedLine);
		loaders.put("ANIMAL_PRODUCT", this::loadAnimalProductLine);
		loaders.put("FARM_PRODUCT", this::loadFarmProductLine);
		loaders.put("ANIMAL", this::loadAnimalLine);
		loaders.put("PLANT", this::loadPlantLine);
	}

	private void loadDevModeLine(String[] parts) {
		if (parts.length > 1) {
			currentUser.setDevMode(Boolean.parseBoolean(parts[1]));
		}
	}

	private void loadPlayerLine(String[] parts) {
		player.setMoney(Double.parseDouble(parts[1]));
		player.setDay(Integer.parseInt(parts[2]));
		player.setCurrMapIndex(Integer.parseInt(parts[3]));
		player.getPosition().moveTo(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
		player.setCurrTile(parts[6].charAt(0));
	}

	private void loadToolLine(String[] parts) {
		String toolName = parts[1];
		int toolQuantity = Integer.parseInt(parts[2]);
		int toolPrice = lookupToolPrice(toolName);
		player.getInventory().add(new ItemStack(new Tool(toolName, toolPrice), toolQuantity));
	}

	private int lookupToolPrice(String toolName) {
		for (Tool template : GameCatalog.getTools()) {
			if (template.getName().equals(toolName)) {
				return (int) template.getPrice();
			}
		}
		return 0;
	}

	private void loadSeedLine(String[] parts) {
		String seedName = parts[1];
		double seedPrice = Double.parseDouble(parts[2]);
		char seedSymbol = parts[3].charAt(0);
		int seedGrowthTime = Integer.parseInt(parts[4]);
		int seedQuantity = Integer.parseInt(parts[5]);
		player.getInventory().add(new ItemStack(
			new PlantSeed(seedName, seedPrice, seedSymbol, seedGrowthTime),
			seedQuantity
		));
	}

	private void loadAnimalProductLine(String[] parts) {
		String name = parts[1];
		double price = Double.parseDouble(parts[2]);
		AnimalProductGrade grade = AnimalProductGrade.fromLevel(Integer.parseInt(parts[3]));
		int quantity = Integer.parseInt(parts[4]);
		player.getInventory().add(new ItemStack(new AnimalProduct(name, (int) price, grade), quantity));
	}

	private void loadFarmProductLine(String[] parts) {
		String name = parts[1];
		double price = Double.parseDouble(parts[2]);
		int freshness = Integer.parseInt(parts[3]);
		int quantity = Integer.parseInt(parts[4]);
		player.getInventory().add(new ItemStack(new FarmProduct(name, price, freshness), quantity));
	}

	private void loadAnimalLine(String[] parts) {
		char symbol = parts[1].charAt(0);
		String name = parts[2];
		String type = parts[3];
		String productName = parts[4];
		int harvestRate = Integer.parseInt(parts[5]);
		int x = Integer.parseInt(parts[6]);
		int y = Integer.parseInt(parts[7]);
		double price = Double.parseDouble(parts[8]);
		boolean harvestable = Boolean.parseBoolean(parts[9]);

		int defaultHarvestRate = lookupDefaultHarvestRate(type, harvestRate);
		AnimalProductDefinition productKind = lookupAnimalProductKind(productName);

		Animal animal = AnimalFactoryProvider.getFactory(type).createAnimal(
			name,
			productKind,
			harvestRate,
			defaultHarvestRate,
			x,
			y,
			price,
			harvestable
		);
		player.getAnimals().add(animal);
		MapBoardState.placeAnimal(x, y, symbol);
	}

	private int lookupDefaultHarvestRate(String type, int fallback) {
		for (Animal template : GameCatalog.getAnimals()) {
			if (template.getType().equals(type)) {
				return template.getDefaultHarvestRate();
			}
		}
		return fallback;
	}

	private AnimalProductDefinition lookupAnimalProductKind(String name) {
		for (AnimalProductDefinition kind : GameCatalog.getAnimalProducts()) {
			if (kind.getName().equals(name)) {
				return kind;
			}
		}
		return new AnimalProductDefinition(name, 0.0);
	}

	private void loadPlantLine(String[] parts) {
		char symbol = parts[1].charAt(0);
		String name = parts[2];
		int x = Integer.parseInt(parts[3]);
		int y = Integer.parseInt(parts[4]);
		int growthTime = Integer.parseInt(parts[5]);
		double price = Double.parseDouble(parts[6]);
		boolean harvestable = Boolean.parseBoolean(parts[7]);
		Plant plant = PlantFactoryProvider.getFactory(name).createPlant(x, y, growthTime, price, harvestable);
		player.getPlants().add(plant);
		MapBoardState.placePlant(x, y, symbol, harvestable);
	}
}
