package viewmodel;

import java.util.List;

import model.Coordinate;
import model.Player;
import model.PlayerItem;
import model.animal.Animal;
import model.item.Item;
import model.item.PlantSeed;
import model.plants.Plant;

public class PlayerViewModel {
    private Player player;

    public PlayerViewModel(Player player) { 
        this.player = player; 
    }

    public double getMoney() { 
        return player.getMoney(); 
    }

    public void setMoney(double value) { 
        player.setMoney(value); 
    }

    public List<PlayerItem> getInventory() { 
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

    public Player getPlayer() { 
        return player; 
    }

    public void addMoney(double value) { 
        player.addMoney(value); 
    }

    public boolean spendMoney(double amount) { 
        return player.spendMoney(amount); 
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

    public <T extends Item> List<PlayerItem> findItemsByType(Class<T> type) {
        return player.findItemsByType(type);
    }

    public List<PlantSeed> getPlayerSeeds() {
        return player.getPlayerSeeds();
    }

    public void removeSeedFromInventory(PlantSeed seed) {
        player.removeSeedFromInventory(seed);
    }

    public int findAnimalProductIndex(int displayChoice) {
        return player.findAnimalProductIndex(displayChoice);
    }

    public int findFarmProductIndex(int displayChoice) {
        return player.findFarmProductIndex(displayChoice);
    }

    public boolean isAnimalNameTaken(String name) {
        return player.isAnimalNameTaken(name);
    }

    public void advanceDay() { 
        player.advanceDay(); 
    }
}
