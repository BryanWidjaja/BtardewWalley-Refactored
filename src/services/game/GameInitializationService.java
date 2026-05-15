package services.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import command.*;
import database.DatabaseRegistry;
import model.Player;
import model.User;
import model.item.AnimalProduct;
import model.item.AnimalProductGrade;
import model.item.PlantSeed;
import model.item.Tool;
import services.StoreService;
import services.UserRepository;
import ui.view.*;
import util.ConsoleUtils;
import util.GradeUtils;
import viewmodel.*;

public class GameInitializationService {
    private Scanner scanner;
    private Random random;
    private ConsoleUtils consoleUtils;
    private User currentUser;
    private Player player;
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
        setupCommands(setupViews());
        
        mainViewModel.init(currentUser);
    }

    private void authenticateUser() {
        UserRepository userRepository = new UserRepository("system_data/users.txt");
        AuthView authView = new AuthView(scanner, consoleUtils, userRepository);
        currentUser = authView.showMainMenu();
    }

    private void setupCore() {
        player = new Player(currentUser.getUsername(), "");
        StoreService storeService = new StoreService();
        GradeUtils gradeUtils = new GradeUtils(random, player);

        playerViewModel = new PlayerViewModel(player);
        mapViewModel = new MapViewModel(playerViewModel, random, gradeUtils);
        storeViewModel = new StoreViewModel(storeService, playerViewModel, mapViewModel, gradeUtils);
        mainViewModel = new MainViewModel(playerViewModel, mapViewModel, storeViewModel);
    }

    private Map<String, Object> setupViews() {
        Map<String, Object> views = new HashMap<>();
        views.put("tool", new ToolStoreView(scanner, consoleUtils));
        views.put("animal", new AnimalStoreView(scanner, consoleUtils));
        views.put("farm", new FarmStoreView(scanner, consoleUtils));
        views.put("sleep", new SleepView(scanner, consoleUtils));
        views.put("plant", new PlantView(scanner, consoleUtils));
        views.put("harvest", new AnimalHarvestView(scanner, consoleUtils));
        views.put("inventory", new InventoryView(scanner, consoleUtils));
        return views;
    }

    private void setupCommands(Map<String, Object> views) {
        eventCommands = new HashMap<>();
        eventCommands.put(GameEvent.TOOL_STORE, () -> ((ToolStoreView)views.get("tool")).show(storeViewModel, playerViewModel));
        eventCommands.put(GameEvent.ANIMAL_STORE, () -> ((AnimalStoreView)views.get("animal")).show(storeViewModel, playerViewModel));
        eventCommands.put(GameEvent.FARM_STORE, () -> ((FarmStoreView)views.get("farm")).show(storeViewModel, playerViewModel));
        eventCommands.put(GameEvent.INVENTORY, () -> ((InventoryView)views.get("inventory")).show(playerViewModel));
        eventCommands.put(GameEvent.SLEEP, () -> {
            if (((SleepView)views.get("sleep")).showSleepPrompt()) mapViewModel.sleep();
        });
        eventCommands.put(GameEvent.PLANT_PROMPT, () -> ((PlantView)views.get("plant")).showPlantPrompt(playerViewModel, mapViewModel));
        eventCommands.put(GameEvent.COLLECT_ANIMAL, () -> ((AnimalHarvestView)views.get("harvest")).showCollectAnimal(playerViewModel, mapViewModel));
        eventCommands.put(GameEvent.EXIT, mainViewModel::shutdown);

        devModeCommands = new HashMap<>();
        devModeCommands.put('r', new DevModeCommand(currentUser, () -> {
            if (((SleepView)views.get("sleep")).showSleepPrompt()) mapViewModel.sleep();
        }));
        devModeCommands.put('g', new DevModeCommand(currentUser, () -> {
            for (int i = 0; i < 20; i++) mapViewModel.sleep();
        }));
        devModeCommands.put('u', new DevModeCommand(currentUser, () -> {
            String[] types = {"Chicken", "Chicken", "Chicken", "Sheep", "Sheep", "Sheep", "Cow", "Cow", "Cow"};
            for (int i = 0; i < types.length; i++) mapViewModel.insertAnimal(types[i], types[i] + (i % 3 + 1));
        }));
        devModeCommands.put('t', new DevModeCommand(currentUser, () -> {
            for (Tool tool : DatabaseRegistry.getList(Tool.class)) {
                playerViewModel.addItem(new Tool(tool.getName(), (int) tool.getPrice()), 1);
            }
            DatabaseRegistry.getList(Tool.class).clear();
        }));
        devModeCommands.put('p', new DevModeCommand(currentUser, () -> {
            AnimalProductGrade grade = storeViewModel.getGradeUtils().getGrade();
            playerViewModel.addItem(new AnimalProduct("Egg", 100, grade), 1);
            playerViewModel.addItem(new AnimalProduct("Milk", 300, grade), 1);
            playerViewModel.addItem(new AnimalProduct("Wool", 800, grade), 1);
        }));
        devModeCommands.put('k', new DevModeCommand(currentUser, () -> {
            playerViewModel.addItem(new PlantSeed("Wheat", 50, 'w', 3), 10);
        }));
        devModeCommands.put('1', new DevModeCommand(currentUser, () -> mapViewModel.devModeTeleport(0)));
        devModeCommands.put('2', new DevModeCommand(currentUser, () -> mapViewModel.devModeTeleport(1)));
        devModeCommands.put('3', new DevModeCommand(currentUser, () -> mapViewModel.devModeTeleport(2)));
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
