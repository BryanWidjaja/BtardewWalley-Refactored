package services.loaders;

import database.ToolDatabase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import models.Animal;
import models.Plant;
import models.Player;
import models.PlayerItem;
import models.User;
import models.items.AnimalProduct;
import models.items.FarmProduct;
import models.items.PlantSeed;
import models.items.Tool;
import views.GameMapView;

public class PlayerDataLoader {
	private Player player;
	private User currentUser;

	public PlayerDataLoader(Player player, User currentUser) {
		super();
		this.player = player;
		this.currentUser = currentUser;
	}

	public void loadPlayerData () {
		File saveFile = new File("user_data/" + currentUser.getUsername() + "_data.txt");
		if (!saveFile.exists()) return;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(saveFile));
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("#");
				String type = parts[0];
				
				if (type.equals("DEVMODE")) {
					if (parts.length > 1) {
						currentUser.setDevMode(Boolean.parseBoolean(parts[1]));
					}
					continue;
				}
				
				switch (type) {
					case "PLAYER":
						player.setMoney(Double.parseDouble(parts[1]));
						player.setDay(Integer.parseInt(parts[2]));
						player.setCurrMapIndex(Integer.parseInt(parts[3]));
						player.getPosition().moveTo(
							Integer.parseInt(parts[4]),
							Integer.parseInt(parts[5])
						);
						player.setCurrTile(parts[6].charAt(0));
						break;
					
					case "TOOL":
						String toolName = parts[1];
						int toolQty = Integer.parseInt(parts[2]);
						Tool loadedTool = new Tool(toolName, 0);
						player.getInventory().add(new PlayerItem(
								new Tool(toolName, (int) loadedTool.getPrice()), toolQty));
						break;
					
					case "SEED":
						String seedName = parts[1];
						double seedPrice = Double.parseDouble(parts[2]);
						char seedSymbol = parts[3].charAt(0);
						int seedGrowth = Integer.parseInt(parts[4]);
						int seedQty = Integer.parseInt(parts[5]);
						player.getInventory().add(new PlayerItem(
								new PlantSeed(seedName, seedPrice, seedSymbol, seedGrowth), seedQty));
						break;
					
					case "ANIMAL_PRODUCT":
						String apName = parts[1];
						double apPrice = Double.parseDouble(parts[2]);
						int apGrade = Integer.parseInt(parts[3]);
						int apQty = Integer.parseInt(parts[4]);
						player.getInventory().add(new PlayerItem(
								new AnimalProduct(apName, (int) apPrice, apGrade), apQty));
						break;
					
					case "FARM_PRODUCT":
						String fpName = parts[1];
						double fpPrice = Double.parseDouble(parts[2]);
						int fpFreshness = Integer.parseInt(parts[3]);
						int fpQty = Integer.parseInt(parts[4]);
						player.getInventory().add(new PlayerItem(
								new FarmProduct(fpName, fpPrice, fpFreshness), fpQty));
						break;
					
					case "ANIMAL":
						char aSymbol = parts[1].charAt(0);
						String aName = parts[2];
						String aType = parts[3];
						String aProduct = parts[4];
						int aHarvestRate = Integer.parseInt(parts[5]);
						int aX = Integer.parseInt(parts[6]);
						int aY = Integer.parseInt(parts[7]);
						double aPrice = Double.parseDouble(parts[8]);
						boolean aHarvestable = Boolean.parseBoolean(parts[9]);
						Animal animal = new Animal(aSymbol, aName, aType, aProduct, aHarvestRate, aX, aY, aPrice, aHarvestable);
						player.getAnimals().add(animal);
						GameMapView.ANIMAL_FARM_MAP[aX][aY] = aSymbol;
						break;
					
					case "PLANT":
						char pSymbol = parts[1].charAt(0);
						String pName = parts[2];
						int pX = Integer.parseInt(parts[3]);
						int pY = Integer.parseInt(parts[4]);
						int pGrowthTime = Integer.parseInt(parts[5]);
						double pPrice = Double.parseDouble(parts[6]);
						boolean pHarvestable = Boolean.parseBoolean(parts[7]);
						Plant plant = new Plant(pSymbol, pName, pX, pY, pGrowthTime, pPrice, pHarvestable);
						player.getPlants().add(plant);
						if (pHarvestable) {
							GameMapView.PLANT_FARM_MAP[pX][pY] = Character.toUpperCase(pSymbol);
						} else {
							GameMapView.PLANT_FARM_MAP[pX][pY] = pSymbol;
						}
						break;
				}
			}
			
			br.close();
			
			Iterator<Tool> it = ToolDatabase.getDatabase().getTools().iterator();
			while (it.hasNext()) {
				Tool tool = it.next();
				for (PlayerItem item : player.getInventory()) {
					if (item.getItem() instanceof Tool && item.getItem().getName().equals(tool.getName())) {
						it.remove();
						break;
					}
				}
			}
			
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			System.out.println("Error loading player data!");
		}
	}
}
