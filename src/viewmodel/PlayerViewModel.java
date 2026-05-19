package viewmodel;

import java.util.List;
import java.util.Locale;
import model.Coordinate;
import model.animal.Animal;
import model.item.Item;
import model.item.ItemStack;
import model.item.plantseed.PlantSeed;
import model.plant.Plant;
import model.player.Player;

public class PlayerViewModel {
    private final Player player;

    public PlayerViewModel(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public double getMoney() {
        return player.getMoney();
    }

    public void setMoney(double value) {
        player.setMoney(value);
    }

    public String getFormattedMoney() {
        return String.format(Locale.ROOT, "%.2f$", player.getMoney());
    }

    public void addMoney(double value) {
        player.addMoney(value);
    }

    public boolean spendMoney(double amount) {
        return player.spendMoney(amount);
    }

    public List<ItemStack> getInventory() {
        return player.getInventory();
    }

    public List<Animal> getAnimals() {
        return player.getAnimals();
    }

    public List<Plant> getPlants() {
        return player.getPlants();
    }

    public int getDay() {
        return player.getDay();
    }

    public Coordinate getPosition() {
        return player.getPosition();
    }

    public int getCurrMapIndex() {
        return player.getCurrMapIndex();
    }

    public void setCurrMapIndex(int index) {
        player.setCurrMapIndex(index);
    }

    public char getCurrTile() {
        return player.getCurrTile();
    }

    public void setCurrTile(char tile) {
        player.setCurrTile(tile);
    }

    public String getName() {
        return player.getName();
    }

    public void addItem(Item item, int quantity) {
        player.addItem(item, quantity);
    }

    public void removeItemQuantity(int index, int quantity) {
        player.removeItemQuantity(index, quantity);
    }

    public boolean hasItem(String itemName) {
        return player.hasItem(itemName);
    }

    public <T extends Item> List<ItemStack> findItemsByType(Class<T> type) {
        return player.findItemsByType(type);
    }

    public <T extends Item> int findItemIndex(Class<T> type, int displayChoice) {
        return player.findItemIndex(type, displayChoice);
    }

    public List<PlantSeed> getPlayerSeeds() {
        return player.getPlayerSeeds();
    }

    public void removeSeedFromInventory(PlantSeed seed) {
        player.removeSeedFromInventory(seed);
    }

    public Animal findAnimalAt(int x, int y) {
        return player.findAnimalAt(x, y);
    }

    public boolean isAnimalNameTaken(String name) {
        return player.isAnimalNameTaken(name);
    }

    public void advanceDay() {
        player.advanceDay();
    }
}
