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
import model.plants.Plant;

public class PlayerDataSaver {
	private static final String DATA_DIR = "user_data";

	private final Player player;
	private final User currentUser;

	public PlayerDataSaver(Player player, User currentUser) {
		this.player = player;
		this.currentUser = currentUser;
	}

	public void savePlayerData() {
		ensureDataDirectory();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFilePath()))) {
			writeStats(writer);
			writeInventory(writer);
			writeAnimals(writer);
			writePlants(writer);
			writeDevModeFlag(writer);
		} catch (IOException exception) {
			System.out.println("Error saving player data!");
		}
	}

	private void ensureDataDirectory() {
		File directory = new File(DATA_DIR);
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	private String saveFilePath() {
		return DATA_DIR + "/" + currentUser.getUsername() + "_data.txt";
	}

	private void writeStats(BufferedWriter writer) throws IOException {
		writer.write(String.format(
			Locale.ROOT,
			"PLAYER#%.2f#%d#%d#%d#%d#%c",
			player.getMoney(),
			player.getDay(),
			player.getCurrMapIndex(),
			player.getPosition().getX(),
			player.getPosition().getY(),
			player.getCurrTile()
		));
		writer.newLine();
	}

	private void writeInventory(BufferedWriter writer) throws IOException {
		for (PlayerItem item : player.getInventory()) {
			writer.write(item.getItem().toSaveLine(item.getQuantity()));
			writer.newLine();
		}
	}

	private void writeAnimals(BufferedWriter writer) throws IOException {
		for (Animal animal : player.getAnimals()) {
			writer.write(animal.toSaveLine());
			writer.newLine();
		}
	}

	private void writePlants(BufferedWriter writer) throws IOException {
		for (Plant plant : player.getPlants()) {
			writer.write(plant.toSaveLine());
			writer.newLine();
		}
	}

	private void writeDevModeFlag(BufferedWriter writer) throws IOException {
		if (currentUser != null && currentUser.isDevMode()) {
			writer.write(String.format(Locale.ROOT, "DEVMODE#%b", true));
			writer.newLine();
		}
	}
}
