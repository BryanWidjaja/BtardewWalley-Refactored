package services.game;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import command.Command;
import model.Player;
import model.User;
import services.StoreService;
import services.UserRepository;
import ui.view.AuthView;
import util.ConsoleUtils;
import util.GradeUtils;
import viewmodel.GameEvent;
import viewmodel.MainViewModel;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class GameInitializationService {
    private Scanner scanner;
    private Random random;
    private ConsoleUtils consoleUtils;
    private User currentUser;
    private PlayerViewModel playerViewModel;
    private MapViewModel mapViewModel;
    private StoreViewModel storeViewModel;
    private MainViewModel mainViewModel;
    private Map<GameEvent, Command> eventCommands;
    private Map<Character, Command> devModeCommands;

    public void initialize() {
        scanner = new Scanner(System.in);
        random = new Random();
        consoleUtils = new ConsoleUtils(scanner);

        authenticateUser();
        setupCore();
        setupCommands();

        mainViewModel.init(currentUser);
    }

    private void authenticateUser() {
        UserRepository userRepository = new UserRepository("system_data/users.txt");
        AuthView authView = new AuthView(scanner, consoleUtils, userRepository);
        currentUser = authView.showMainMenu();
    }

    private void setupCore() {
        Player player = new Player(currentUser.getUsername(), "");
        StoreService storeService = new StoreService();
        GradeUtils gradeUtils = new GradeUtils(random, player);

        playerViewModel = new PlayerViewModel(player);
        mapViewModel = new MapViewModel(playerViewModel, random, gradeUtils);
        storeViewModel = new StoreViewModel(storeService, playerViewModel, mapViewModel, gradeUtils);
        mainViewModel = new MainViewModel(playerViewModel, mapViewModel, storeViewModel);
    }

    private void setupCommands() {
        Views views = new Views(scanner, consoleUtils);
        CommandFactory factory = new CommandFactory(
                currentUser, playerViewModel, mapViewModel, storeViewModel, mainViewModel, views);
        eventCommands = factory.createEventCommands();
        devModeCommands = factory.createDevModeCommands();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public ConsoleUtils getConsoleUtils() {
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
