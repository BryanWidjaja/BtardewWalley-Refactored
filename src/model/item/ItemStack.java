package model.item;

public class ItemStack {
	private Item item;
	private int quantity;

	public ItemStack(Item item, int quantity) {
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
