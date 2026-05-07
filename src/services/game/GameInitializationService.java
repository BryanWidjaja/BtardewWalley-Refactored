package services.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import commands.*;
import databases.ToolDatabase;
import models.Player;
import models.User;
import models.items.AnimalProduct;
import models.items.PlantSeed;
import models.items.Tool;
import services.StoreService;
import ui.views.*;
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
        AuthView authView = new AuthView(scanner, consoleUtils);
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
        eventCommands.put(GameEvent.TOOL_STORE, new OpenToolStoreCommand((ToolStoreView)views.get("tool"), storeViewModel, playerViewModel));
        eventCommands.put(GameEvent.ANIMAL_STORE, new OpenAnimalStoreCommand((AnimalStoreView)views.get("animal"), storeViewModel, playerViewModel));
        eventCommands.put(GameEvent.FARM_STORE, new OpenFarmStoreCommand((FarmStoreView)views.get("farm"), storeViewModel, playerViewModel));
        eventCommands.put(GameEvent.INVENTORY, new OpenInventoryCommand((InventoryView)views.get("inventory"), playerViewModel));
        eventCommands.put(GameEvent.SLEEP, new SleepCommand((SleepView)views.get("sleep"), mapViewModel));
        eventCommands.put(GameEvent.PLANT_PROMPT, new PlantSeedCommand((PlantView)views.get("plant"), playerViewModel, mapViewModel));
        eventCommands.put(GameEvent.COLLECT_ANIMAL, new CollectAnimalCommand((AnimalHarvestView)views.get("harvest"), playerViewModel, mapViewModel));
        eventCommands.put(GameEvent.EXIT, new ExitGameCommand(mainViewModel));

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
            for (Tool tool : ToolDatabase.getDatabase().getTools()) {
                playerViewModel.addItem(new Tool(tool.getName(), (int) tool.getPrice()), 1);
            }
            ToolDatabase.getDatabase().getTools().clear();
        }));
        devModeCommands.put('p', new DevModeCommand(currentUser, () -> {
            GradeUtils gradeUtils = storeViewModel.getGradeUtils();
            int grade = gradeUtils.getGrade();
            playerViewModel.addItem(new AnimalProduct("Egg", (int) (100 * gradeUtils.getGradeMultiplier(grade)), grade), 1);
            playerViewModel.addItem(new AnimalProduct("Milk", (int) (300 * gradeUtils.getGradeMultiplier(grade)), grade), 1);
            playerViewModel.addItem(new AnimalProduct("Wool", (int) (900 * gradeUtils.getGradeMultiplier(grade)), grade), 1);
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
