package model.player;

import java.util.ArrayList;
import java.util.List;
import iterator.Iterator;
import iterator.ListIterator;
import model.animal.Animal;
import model.item.Item;
import model.item.ItemStack;
import model.item.plantseed.PlantSeed;
import model.item.tool.Tool;
import model.plant.Plant;

public class PlayerInventory {
    private ArrayList<ItemStack> items;
    private ArrayList<Animal> animals;
    private ArrayList<Plant> plants;

    public PlayerInventory() {
        this.items = new ArrayList<>();
        this.animals = new ArrayList<>();
        this.plants = new ArrayList<>();
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void addItem(Item newItem, int quantity) {
        if (newItem instanceof Tool) {
            if (hasItem(newItem.getName())) {
                return;
            }
            items.add(new ItemStack(newItem, 1));
            return;
        }
        for (ItemStack existing : items) {
            if (existing.getItem().canStackWith(newItem)) {
                existing.addQuantity(quantity);
                return;
            }
        }
        items.add(new ItemStack(newItem, quantity));
    }

    public boolean hasItem(String itemName) {
        for (ItemStack item : items) {
            if (item.getItem().getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public <T extends Item> List<ItemStack> findItemsByType(Class<T> type) {
        List<ItemStack> result = new ArrayList<>();
        for (ItemStack item : items) {
            if (type.isInstance(item.getItem())) {
                result.add(item);
            }
        }
        return result;
    }

    public void removeItemQuantity(int index, int quantity) {
        ItemStack item = items.get(index);
        item.removeQuantity(quantity);
        if (item.isEmpty()) {
            items.remove(index);
        }
    }

    public List<PlantSeed> getPlayerSeeds() {
        List<PlantSeed> seeds = new ArrayList<>();
        for (ItemStack item : items) {
            if (item.getItem() instanceof PlantSeed) {
                seeds.add((PlantSeed) item.getItem());
            }
        }
        return seeds;
    }

    public void removeSeedFromInventory(PlantSeed seed) {
        Iterator<ItemStack> iterator = new ListIterator<>(items);
        while (iterator.hasNext()) {
            ItemStack item = iterator.getNext();
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
