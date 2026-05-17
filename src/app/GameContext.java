package app;

import model.User;
import model.map.MapBoardState;
import services.loader.AnimalLoader;
import services.loader.SeedLoader;
import services.loader.ToolLoader;
import services.repository.PlayerRepository;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class GameContext {
    private PlayerViewModel playerViewModel;
    private MapViewModel mapViewModel;
    private StoreViewModel storeViewModel;
    private PlayerRepository persistenceService;

    public GameContext(PlayerViewModel playerViewModel, MapViewModel mapViewModel, StoreViewModel storeViewModel) {
        this.playerViewModel = playerViewModel;
        this.mapViewModel = mapViewModel;
        this.storeViewModel = storeViewModel;
    }

    public PlayerViewModel getPlayerViewModel() {
        return playerViewModel;
    }

    public MapViewModel getMapViewModel() {
        return mapViewModel;
    }

    public StoreViewModel getStoreViewModel() {
        return storeViewModel;
    }

    public void init(User user) {
        MapBoardState.registerMaps();
        ToolLoader.loadAvailableTools();
        AnimalLoader.loadAvailableAnimals();
        SeedLoader.loadAvailableSeeds();

        persistenceService = new PlayerRepository(
                playerViewModel.getPlayer(), user);
        persistenceService.load();
    }

    public void shutdown() {
        if (persistenceService != null) {
            persistenceService.save();
        }
        System.out.println("Game saved successfully! Goodbye!");
        System.exit(0);
    }
}
