package model;

import java.util.ArrayList;
import java.util.List;
import iterator.Iterator;
import iterator.ListIterator;
import model.animal.Animal;
import model.item.Item;
import model.item.PlantSeed;
import model.plants.Plant;

public class PlayerInventory {
    private ArrayList<PlayerItem> items;
    private ArrayList<Animal> animals;
    private ArrayList<Plant> plants;

    public PlayerInventory() {
        this.items = new ArrayList<>();
        this.animals = new ArrayList<>();
        this.plants = new ArrayList<>();
    }

    public List<PlayerItem> getItems() {
        return items;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void addItem(Item newItem, int quantity) {
        for (PlayerItem existing : items) {
            if (existing.getItem().canStackWith(newItem)) {
                existing.addQuantity(quantity);
                return;
            }
        }
        items.add(new PlayerItem(newItem, quantity));
    }

    public boolean hasItem(String itemName) {
        for (PlayerItem item : items) {
            if (item.getItem().getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public <T extends Item> List<PlayerItem> findItemsByType(Class<T> type) {
        List<PlayerItem> result = new ArrayList<>();
        for (PlayerItem item : items) {
            if (type.isInstance(item.getItem())) {
                result.add(item);
            }
        }
        return result;
    }

    public void removeItemQuantity(int index, int quantity) {
        PlayerItem item = items.get(index);
        item.removeQuantity(quantity);
        if (item.isEmpty()) {
            items.remove(index);
        }
    }

    public List<PlantSeed> getPlayerSeeds() {
        List<PlantSeed> seeds = new ArrayList<>();
        for (PlayerItem item : items) {
            if (item.getItem() instanceof PlantSeed) {
                seeds.add((PlantSeed) item.getItem());
            }
        }
        return seeds;
    }

    public void removeSeedFromInventory(PlantSeed seed) {
        Iterator<PlayerItem> iterator = new ListIterator<>(items);
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

    public <T extends Item> int findItemIndex(Class<T> type, int displayChoice) {
        int counter = 1;
        for (int i = 0; i < items.size(); i++) {
            if (type.isInstance(items.get(i).getItem())) {
                if (counter == displayChoice) {
                    return i;
                }
                counter++;
            }
        }
        return -1;
    }

    public Animal findAnimalAt(int x, int y) {
        for (Animal animal : animals) {
            if (animal.getPosition().getX() == x && animal.getPosition().getY() == y) {
                return animal;
            }
        }
        return null;
    }

    public boolean isAnimalNameTaken(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }
        return false;
    }
}
