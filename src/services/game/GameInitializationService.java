package services.game;

import java.util.Map;

import command.Command;
import model.Player;
import model.User;
import services.StoreService;
import services.UserRepository;
import ui.view.AuthView;
import util.ConsoleIO;
import util.GradeUtils;
import viewmodel.GameEvent;
import viewmodel.MainViewModel;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class GameInitializationService {
    private ConsoleIO io;
    private User currentUser;
    private PlayerViewModel playerViewModel;
    private MapViewModel mapViewModel;
    private StoreViewModel storeViewModel;
    private MainViewModel mainViewModel;
    private Map<GameEvent, Command> eventCommands;
    private Map<Character, Command> devModeCommands;

    public void initialize() {
        io = new ConsoleIO();

        authenticateUser();
        setupCore();
        setupCommands();

        mainViewModel.init(currentUser);
    }

    private void authenticateUser() {
        UserRepository userRepository = new UserRepository("system_data/users.txt");
        AuthView authView = new AuthView(io.getScanner(), io.getConsoleUtils(), userRepository);
        currentUser = authView.showMainMenu();
    }

    private void setupCore() {
        Player player = new Player(currentUser.getUsername(), "");
        StoreService storeService = new StoreService();
        GradeUtils gradeUtils = new GradeUtils(io.getRandom());

        playerViewModel = new PlayerViewModel(player);
        mapViewModel = new MapViewModel(playerViewModel, io.getRandom(), gradeUtils);
        storeViewModel = new StoreViewModel(storeService, playerViewModel, mapViewModel, gradeUtils);
        mainViewModel = new MainViewModel(playerViewModel, mapViewModel, storeViewModel);
    }

    private void setupCommands() {
        Views views = new Views(io.getScanner(), io.getConsoleUtils());
        eventCommands = new EventCommandRegistry(
                playerViewModel, mapViewModel, storeViewModel, mainViewModel, views).build();
        devModeCommands = new DevModeCommandRegistry(
                currentUser, playerViewModel, mapViewModel, storeViewModel, views).build();
    }

    public ConsoleIO getIO() {
        return io;
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
