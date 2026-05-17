package services.game;

import java.util.Map;

import command.Command;
import registry.DevModeCommandRegistry;
import registry.EventCommandRegistry;
import services.repository.StoreRepository;
import services.repository.UserRepository;
import model.User;
import model.item.animalproduct.AnimalProductGradeRoller;
import ui.view.AuthView;
import util.ConsoleUtils;
import model.player.Player;
import model.GameEvent;
import app.GameContext;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class GameInitializationService {
    private ConsoleUtils consoleUtils;
    private User currentUser;
    private PlayerViewModel playerViewModel;
    private MapViewModel mapViewModel;
    private StoreViewModel storeViewModel;
    private GameContext mainViewModel;
    private Map<GameEvent, Command> eventCommands;
    private Map<Character, Command> devModeCommands;

    public void initialize() {
        consoleUtils = new ConsoleUtils();

        authenticateUser();
        setupCore();
        setupCommands();

        mainViewModel.init(currentUser);
    }

    private void authenticateUser() {
        UserRepository userRepository = new UserRepository("system_data/users.txt");
        AuthView authView = new AuthView(consoleUtils.getScanner(), consoleUtils, userRepository);
        currentUser = authView.showMainMenu();
    }

    private void setupCore() {
        Player player = new Player(currentUser.getUsername(), "");
        StoreRepository storeService = new StoreRepository();
        AnimalProductGradeRoller gradeUtils = new AnimalProductGradeRoller(consoleUtils.getRandom());

        playerViewModel = new PlayerViewModel(player);
        mapViewModel = new MapViewModel(playerViewModel, consoleUtils.getRandom(), gradeUtils);
        storeViewModel = new StoreViewModel(storeService, playerViewModel, mapViewModel, gradeUtils);
        mainViewModel = new GameContext(playerViewModel, mapViewModel, storeViewModel);
    }

    private void setupCommands() {
        ViewRegistry views = new ViewRegistry(consoleUtils.getScanner(), consoleUtils);
        eventCommands = new EventCommandRegistry(
                playerViewModel, mapViewModel, storeViewModel, mainViewModel, views).build();
        devModeCommands = new DevModeCommandRegistry(
                currentUser, playerViewModel, mapViewModel, storeViewModel, views).build();
    }

    public ConsoleUtils getIO() {
        return consoleUtils;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public PlayerViewModel getPlayerViewModel() {
        return playerViewModel;
    }

    public MapViewModel getMapViewModel() {
        return mapViewModel;
    }

    public Map<GameEvent, Command> getEventCommands() {
        return eventCommands;
    }

    public Map<Character, Command> getDevModeCommands() {
        return devModeCommands;
    }
}
