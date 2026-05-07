package viewmodel;

import java.util.ArrayList;
import iterators.Iterator;
import java.util.List;

import iterators.AnimalIterator;
import iterators.InventoryIterator;
import models.Animal;
import models.Coordinate;
import models.Plant;
import models.Player;
import models.PlayerItem;
import models.items.AnimalProduct;
import models.items.FarmProduct;
import models.items.Item;
import models.items.PlantSeed;

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
        List<PlantSeed> seeds = new ArrayList<>();
        for (PlayerItem item : player.getInventory()) {
            if (item.getItem() instanceof PlantSeed) {
                seeds.add((PlantSeed) item.getItem());
            }
        }
        return seeds;
    }

    public void removeSeedFromInventory(PlantSeed seed) {
        Iterator<PlayerItem> iterator = new InventoryIterator(player.getInventory());
        while (iterator.hasNext()) {
            PlayerItem item = iterator.getNext();
            if (item.getItem() instanceof PlantSeed
                    && item.getItem().getName().equals(seed.getName())) {
                item.removeQuantity(1);
                if (item.isEmpty()) {
                    iterator.remove();
                }
                break;
            }
        }
    }

    public int findAnimalProductIndex(int displayChoice) {
        int counter = 1;
        Iterator<PlayerItem> iterator = new InventoryIterator(player.getInventory());
        while (iterator.hasNext()) {
            PlayerItem item = iterator.getNext();
            if (item.getItem() instanceof AnimalProduct) {
                if (counter == displayChoice) {
                    return player.getInventory().indexOf(item);
                }
                counter++;
            }
        }
        return -1;
    }

    public int findFarmProductIndex(int displayChoice) {
        int counter = 1;
        Iterator<PlayerItem> iterator = new InventoryIterator(player.getInventory());
        while (iterator.hasNext()) {
            PlayerItem item = iterator.getNext();
            if (item.getItem() instanceof FarmProduct) {
                if (counter == displayChoice) {
                    return player.getInventory().indexOf(item);
                }
                counter++;
            }
        }
        return -1;
    }

    public boolean isAnimalNameTaken(String name) {
        Iterator<Animal> iterator = new AnimalIterator(player.getAnimals());
        while (iterator.hasNext()) {
            Animal animal = iterator.getNext();
            if (animal.getName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }
        return false;
    }

    public void advanceDay() { 
        player.advanceDay(); 
    }
}
