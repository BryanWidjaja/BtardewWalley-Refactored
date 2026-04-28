package models;

import models.items.Item;

public class PlayerItem {
	private final Item item;
	private int quantity;
	
	public PlayerItem(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void addQuantity(int amount) {
		quantity += amount;
	}

	public void removeQuantity(int amount) {
		quantity -= amount;
	}

	public boolean isEmpty() {
		return quantity <= 0;
	}
}
