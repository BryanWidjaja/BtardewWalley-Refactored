package services.savers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import models.Animal;
import models.Plant;
import models.Player;
import models.PlayerItem;
import models.User;
import models.items.AnimalProduct;
import models.items.FarmProduct;
import models.items.PlantSeed;
import models.items.Tool;

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
			File dir = new File("user_data");
			if (!dir.exists()) {
				dir.mkdir();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter("user_data/" + currentUser.getUsername() + "_data.txt"));
			
			bw.write(String.format("PLAYER#%.2f#%d#%d#%d#%d#%c",
					player.getMoney(), player.getDay(), player.getCurrMapIndex(),
					player.getPosition().getX(), player.getPosition().getY(), player.getCurrTile()));
			bw.newLine();

			for (PlayerItem item : player.getInventory()) {
				if (item.getItem() instanceof Tool) {
					bw.write(String.format("TOOL#%s#%d", item.getItem().getName(), item.getQuantity()));
				} else if (item.getItem() instanceof PlantSeed) {
					PlantSeed seed = (PlantSeed) item.getItem();
					bw.write(String.format("SEED#%s#%.2f#%c#%d#%d",
							seed.getName(), seed.getPrice(), seed.getSymbol(), seed.getGrowthTime(), item.getQuantity()));
				} else if (item.getItem() instanceof AnimalProduct) {
					AnimalProduct ap = (AnimalProduct) item.getItem();
					bw.write(String.format("ANIMAL_PRODUCT#%s#%.2f#%d#%d",
							ap.getName(), ap.getPrice(), ap.getGrade(), item.getQuantity()));
				} else if (item.getItem() instanceof FarmProduct) {
					FarmProduct fp = (FarmProduct) item.getItem();
					bw.write(String.format("FARM_PRODUCT#%s#%.2f#%d#%d",
							fp.getName(), fp.getPrice(), fp.getFreshness(), item.getQuantity()));
				}
				bw.newLine();
			}
			
			for (Animal animal : player.getAnimals()) {
				bw.write(String.format("ANIMAL#%c#%s#%s#%s#%d#%d#%d#%.2f#%b",
						animal.getSymbol(), animal.getName(), animal.getType(), animal.getAnimalProduct(),
						animal.getHarvestRate(), animal.getPosition().getX(), animal.getPosition().getY(),
						animal.getPrice(), animal.isHarvestable()));
				bw.newLine();
			}
			
			for (Plant plant : player.getPlants()) {
				bw.write(String.format("PLANT#%c#%s#%d#%d#%d#%.2f#%b",
						plant.getSymbol(), plant.getName(), plant.getPosition().getX(), plant.getPosition().getY(),
						plant.getGrowthTime(), plant.getPrice(), plant.isHarvestable()));
				bw.newLine();
			}

			// Save dev mode flag if enabled for this user
			if (currentUser != null && currentUser.isDevMode()) {
				bw.write(String.format("DEVMODE#%b", true));
				bw.newLine();
			}
			
			bw.close();
		} catch (IOException e) {
			System.out.println("Error saving player data!");
		}
	}
}
