package services.game;

import java.util.HashMap;
import java.util.Map;

import command.Command;
import command.DevModeCommand;
import database.DatabaseRegistry;
import model.User;
import model.item.AnimalProduct;
import model.item.AnimalProductGrade;
import model.item.PlantSeed;
import model.item.Tool;
import viewmodel.GameEvent;
import viewmodel.MainViewModel;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class CommandFactory {
    private final User currentUser;
    private final PlayerViewModel playerViewModel;
    private final MapViewModel mapViewModel;
    private final StoreViewModel storeViewModel;
    private final MainViewModel mainViewModel;
    private final Views views;

    public CommandFactory(User currentUser, PlayerViewModel playerViewModel, MapViewModel mapViewModel,
                          StoreViewModel storeViewModel, MainViewModel mainViewModel, Views views) {
        this.currentUser = currentUser;
        this.playerViewModel = playerViewModel;
        this.mapViewModel = mapViewModel;
        this.storeViewModel = storeViewModel;
        this.mainViewModel = mainViewModel;
        this.views = views;
    }

    public Map<GameEvent, Command> createEventCommands() {
        Map<GameEvent, Command> commands = new HashMap<>();
        commands.put(GameEvent.TOOL_STORE, () -> views.getToolStore().show(storeViewModel, playerViewModel));
        commands.put(GameEvent.ANIMAL_STORE, () -> views.getAnimalStore().show(storeViewModel, playerViewModel));
        commands.put(GameEvent.FARM_STORE, () -> views.getFarmStore().show(storeViewModel, playerViewModel));
        commands.put(GameEvent.INVENTORY, () -> views.getInventory().show(playerViewModel));
        commands.put(GameEvent.SLEEP, this::sleepIfConfirmed);
        commands.put(GameEvent.PLANT_PROMPT, () -> views.getPlant().showPlantPrompt(playerViewModel, mapViewModel));
        commands.put(GameEvent.COLLECT_ANIMAL, () -> views.getHarvest().showCollectAnimal(playerViewModel, mapViewModel));
        commands.put(GameEvent.EXIT, mainViewModel::shutdown);
        return commands;
    }

    public Map<Character, Command> createDevModeCommands() {
        Map<Character, Command> commands = new HashMap<>();
        commands.put('r', dev(this::sleepIfConfirmed));
        commands.put('g', dev(this::advanceManyDays));
        commands.put('u', dev(this::insertSampleAnimals));
        commands.put('t', dev(this::giveAllTools));
        commands.put('p', dev(this::giveAnimalProducts));
        commands.put('k', dev(this::giveWheatSeeds));
        commands.put('1', dev(() -> mapViewModel.devModeTeleport(0)));
        commands.put('2', dev(() -> mapViewModel.devModeTeleport(1)));
        commands.put('3', dev(() -> mapViewModel.devModeTeleport(2)));
        return commands;
    }

    private DevModeCommand dev(Runnable action) {
        return new DevModeCommand(currentUser, action);
    }

    private void sleepIfConfirmed() {
        if (views.getSleep().showSleepPrompt()) mapViewModel.sleep();
    }

    private void advanceManyDays() {
        for (int i = 0; i < 20; i++) mapViewModel.sleep();
    }

    private void insertSampleAnimals() {
        String[] types = {"Chicken", "Chicken", "Chicken", "Sheep", "Sheep", "Sheep", "Cow", "Cow", "Cow"};
        for (int i = 0; i < types.length; i++) {
            mapViewModel.insertAnimal(types[i], types[i] + (i % 3 + 1));
        }
    }

    private void giveAllTools() {
        for (Tool tool : DatabaseRegistry.getList(Tool.class)) {
            playerViewModel.addItem(new Tool(tool.getName(), (int) tool.getPrice()), 1);
        }
    }

    private void giveAnimalProducts() {
        AnimalProductGrade grade = storeViewModel.getGradeUtils().getGrade();
        playerViewModel.addItem(new AnimalProduct("Egg", 100, grade), 1);
        playerViewModel.addItem(new AnimalProduct("Milk", 300, grade), 1);
        playerViewModel.addItem(new AnimalProduct("Wool", 800, grade), 1);
    }

    private void giveWheatSeeds() {
        playerViewModel.addItem(new PlantSeed("Wheat", 50, 'w', 3), 10);
    }
}
