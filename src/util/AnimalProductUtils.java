package util;

import model.Player;
import model.PlayerItem;
import model.item.AnimalProduct;

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
