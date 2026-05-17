package services.repository;

import model.User;
import model.player.Player;
import services.loader.PlayerDataLoader;
import services.saver.PlayerDataSaver;

public class PlayerRepository {
    private final PlayerDataLoader loader;
    private final PlayerDataSaver saver;

    public PlayerRepository(Player player, User user) {
        this.loader = new PlayerDataLoader(player, user);
        this.saver = new PlayerDataSaver(player, user);
    }

    public void save() {
        saver.savePlayerData();
    }

    public void load() {
        loader.loadPlayerData();
    }
}
