package viewmodel;

import model.User;
import services.PlayerPersistenceService;
import services.loader.AnimalLoader;
import services.loader.SeedLoader;
import services.loader.ToolLoader;
import ui.view.MapBoard;

public class MainViewModel {
    private PlayerViewModel playerViewModel;
    private MapViewModel mapViewModel;
    private StoreViewModel storeViewModel;
    private PlayerPersistenceService persistenceService;

    public MainViewModel(PlayerViewModel playerViewModel, MapViewModel mapViewModel, StoreViewModel storeViewModel) {
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
        MapBoard.registerMaps();
        ToolLoader.loadAvailableTools();
        AnimalLoader.loadAvailableAnimals();
        SeedLoader.loadAvailableSeeds();

        persistenceService = new PlayerPersistenceService(
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
