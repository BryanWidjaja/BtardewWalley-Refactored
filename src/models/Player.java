package models;
import java.util.ArrayList;
import java.util.List;

import models.items.Item;

public class Player {
	private final String name;
	private final String gender;
	private double money;
	private final ArrayList<PlayerItem> inventory;
	private final ArrayList<Animal> animals;
	private final ArrayList<Plant> plants;
	private final Coordinate position;
	private int day;
	private int currMapIndex;
	private char currTile;
	
	public Player(String name, String gender) {
		this.name = name;
		this.gender = gender;
		this.money = 1000.0;
		this.inventory = new ArrayList<>();
		this.animals = new ArrayList<>();
		this.plants = new ArrayList<>();
		this.position = new Coordinate(10, 21);
		this.day = 1;
		currMapIndex = 1;
		currTile = ' ';
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public double getMoney() {
		return money;
	}

	public void addMoney(double amount) {
		money += amount;
	}

	public boolean spendMoney(double amount) {
		if (money < amount) return false;
		money -= amount;
		return true;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public ArrayList<PlayerItem> getInventory() {
		return inventory;
	}

	public ArrayList<Animal> getAnimals() {
		return animals;
	}

	public ArrayList<Plant> getPlants() {
		return plants;
	}

	public Coordinate getPosition() {
		return position;
	}

	public int getDay() {
		return day;
	}

	public void advanceDay() {
		day++;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void addItem(Item newItem, int quantity) {
		for (PlayerItem existing : inventory) {
			if (existing.getItem().getName().equals(newItem.getName())
				&& existing.getItem().getClass().equals(newItem.getClass())) {
				
				if (newItem instanceof models.items.AnimalProduct) {
					models.items.AnimalProduct newAP = (models.items.AnimalProduct) newItem;
					models.items.AnimalProduct existingAP = (models.items.AnimalProduct) existing.getItem();
					if (newAP.getGrade() == existingAP.getGrade()) {
						existing.addQuantity(quantity);
						return;
					}
				} else {
					existing.addQuantity(quantity);
					return;
				}
			}
		}
		inventory.add(new PlayerItem(newItem, quantity));
	}

	public boolean hasItem(String itemName) {
		for (PlayerItem item : inventory) {
			if (item.getItem().getName().equals(itemName)) {
				return true;
			}
		}
		return false;
	}

	public <T extends Item> List<PlayerItem> findItemsByType(Class<T> type) {
		List<PlayerItem> result = new ArrayList<>();
		for (PlayerItem item : inventory) {
			if (type.isInstance(item.getItem())) {
				result.add(item);
			}
		}
		return result;
	}

	public void removeItemQuantity(int index, int quantity) {
		PlayerItem item = inventory.get(index);
		item.removeQuantity(quantity);
		if (item.isEmpty()) {
			inventory.remove(index);
		}
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
}
