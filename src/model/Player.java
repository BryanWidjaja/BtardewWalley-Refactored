package model;

import java.util.List;
import model.animal.Animal;
import model.item.Item;
import model.item.PlantSeed;
import model.plants.Plant;

public class Player {
    private String name;
    private String gender;
    private PlayerWallet wallet;
    private PlayerInventory inventory;
    private Coordinate position;
    private int day;
    private int currMapIndex;
    private char currTile;

    public Player(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.wallet = new PlayerWallet(1000.0);
        this.inventory = new PlayerInventory();
        this.position = new Coordinate(10, 21);
        this.day = 1;
        this.currMapIndex = 1;
        this.currTile = ' ';
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public double getMoney() {
        return wallet.getMoney();
    }

    public void setMoney(double amount) {
        wallet.setMoney(amount);
    }

    public void addMoney(double amount) {
        wallet.addMoney(amount);
    }

    public boolean spendMoney(double amount) {
        return wallet.spendMoney(amount);
    }

    public List<PlayerItem> getInventory() {
        return inventory.getItems();
    }

    public List<Animal> getAnimals() {
        return inventory.getAnimals();
    }

    public List<Plant> getPlants() {
        return inventory.getPlants();
    }

    public Coordinate getPosition() {
        return position;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getCurrMapIndex() {
        return currMapIndex;
    }

    public void setCurrMapIndex(int currMapIndex) {
        this.currMapIndex = currMapIndex;
    }

    public char getCurrTile() {
        return currTile;
    }

    public void setCurrTile(char currTile) {
        this.currTile = currTile;
    }

    public void advanceDay() {
        day++;
    }

    public void addItem(Item item, int quantity) {
        inventory.addItem(item, quantity);
    }

    public boolean hasItem(String itemName) {
        return inventory.hasItem(itemName);
    }

    public <T extends Item> List<PlayerItem> findItemsByType(Class<T> type) {
        return inventory.findItemsByType(type);
    }

    public <T extends Item> int findItemIndex(Class<T> type, int displayChoice) {
        return inventory.findItemIndex(type, displayChoice);
    }

    public void removeItemQuantity(int index, int quantity) {
        inventory.removeItemQuantity(index, quantity);
    }

    public List<PlantSeed> getPlayerSeeds() {
        return inventory.getPlayerSeeds();
    }

    public void removeSeedFromInventory(PlantSeed seed) {
        inventory.removeSeedFromInventory(seed);
    }

    public Animal findAnimalAt(int x, int y) {
        return inventory.findAnimalAt(x, y);
    }

    public boolean isAnimalNameTaken(String name) {
        return inventory.isAnimalNameTaken(name);
    }
}
