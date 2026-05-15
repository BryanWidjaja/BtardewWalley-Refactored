package services;

import model.Player;
import model.User;
import services.loader.PlayerDataLoader;
import services.saver.PlayerDataSaver;

public class PlayerPersistenceService {
    private final PlayerDataLoader loader;
    private final PlayerDataSaver saver;

    public PlayerPersistenceService(Player player, User user) {
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
