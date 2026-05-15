package services.saver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import model.Player;
import model.PlayerItem;
import model.User;
import model.animal.Animal;
import model.item.AnimalProduct;
import model.item.FarmProduct;
import model.item.PlantSeed;
import model.item.Tool;
import model.plants.Plant;

public class PlayerDataSaver {
	private Player player;
	private User currentUser;

	public PlayerDataSaver(Player player, User currentUser) {
		super();
		this.player = player;
		this.currentUser = currentUser;
	}

	public void savePlayerData () {
		try {
			File directory = new File("user_data");
			if (!directory.exists()) {
				directory.mkdir();
			}
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("user_data/" + currentUser.getUsername() + "_data.txt"));
			
			bufferedWriter.write(String.format(Locale.ROOT, "PLAYER#%.2f#%d#%d#%d#%d#%c",
					player.getMoney(), player.getDay(), player.getCurrMapIndex(),
					player.getPosition().getX(), player.getPosition().getY(), player.getCurrTile()));
			bufferedWriter.newLine();

			for (PlayerItem item : player.getInventory()) {
				if (item.getItem() instanceof Tool) {
					bufferedWriter.write(String.format(Locale.ROOT, "TOOL#%s#%d", item.getItem().getName(), item.getQuantity()));
				} else if (item.getItem() instanceof PlantSeed) {
					PlantSeed seed = (PlantSeed) item.getItem();
					bufferedWriter.write(String.format(Locale.ROOT, "SEED#%s#%.2f#%c#%d#%d",
							seed.getName(), seed.getPrice(), seed.getSymbol(), seed.getGrowthTime(), item.getQuantity()));
				} else if (item.getItem() instanceof AnimalProduct) {
					AnimalProduct animalProduct = (AnimalProduct) item.getItem();
					bufferedWriter.write(String.format(Locale.ROOT, "ANIMAL_PRODUCT#%s#%.2f#%d#%d",
							animalProduct.getName(), animalProduct.getPrice(), animalProduct.getGrade().getLevel(), item.getQuantity()));
				} else if (item.getItem() instanceof FarmProduct) {
					FarmProduct farmProduct = (FarmProduct) item.getItem();
					bufferedWriter.write(String.format(Locale.ROOT, "FARM_PRODUCT#%s#%.2f#%d#%d",
							farmProduct.getName(), farmProduct.getPrice(), farmProduct.getFreshness().getLevel(), item.getQuantity()));
				}
				bufferedWriter.newLine();
			}

			for (Animal animal : player.getAnimals()) {
				bufferedWriter.write(String.format(Locale.ROOT, "ANIMAL#%c#%s#%s#%s#%d#%d#%d#%.2f#%b",
						animal.getSymbol(), animal.getName(), animal.getType(), animal.getAnimalProduct(),
						animal.getHarvestRate(), animal.getPosition().getX(), animal.getPosition().getY(),
						animal.getPrice(), animal.isHarvestable()));
				bufferedWriter.newLine();
			}

			for (Plant plant : player.getPlants()) {
				bufferedWriter.write(String.format(Locale.ROOT, "PLANT#%c#%s#%d#%d#%d#%.2f#%b",
						plant.getSymbol(), plant.getName(), plant.getPosition().getX(), plant.getPosition().getY(),
						plant.getGrowthTime(), plant.getPrice(), plant.isHarvestable()));
				bufferedWriter.newLine();
			}

			if (currentUser != null && currentUser.isDevMode()) {
				bufferedWriter.write(String.format(Locale.ROOT, "DEVMODE#%b", true));
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
		} catch (IOException exception) {
			System.out.println("Error saving player data!");
		}
	}
}
