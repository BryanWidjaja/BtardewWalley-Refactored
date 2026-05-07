package services;

import models.Player;
import models.User;
import services.loaders.PlayerDataLoader;
import services.savers.PlayerDataSaver;

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
