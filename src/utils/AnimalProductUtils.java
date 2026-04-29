package utils;

import models.Player;
import models.PlayerItem;
import models.items.AnimalProduct;

public class AnimalProductUtils {
	private Player player;

	public AnimalProductUtils(Player player) {
		super();
		this.player = player;
	}

	public int findAnimalProduct(int choice) {
		int counter = 1;
		for (PlayerItem item : player.getInventory()) {
            if (item.getItem() instanceof AnimalProduct) {
                if (counter == choice) {
                	return player.getInventory().indexOf(item);
                }
                
                counter++;
            }
        }
		
		return -1;
	}
}
