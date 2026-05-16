package services.game;

import java.util.HashMap;
import java.util.Map;

import command.Command;
import command.SleepWithPromptCommand;
import viewmodel.GameEvent;
import viewmodel.MainViewModel;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class EventCommandRegistry {
    private final PlayerViewModel playerViewModel;
    private final MapViewModel mapViewModel;
    private final StoreViewModel storeViewModel;
    private final MainViewModel mainViewModel;
    private final Views views;

    public EventCommandRegistry(
        PlayerViewModel playerViewModel,
        MapViewModel mapViewModel,
        StoreViewModel storeViewModel,
        MainViewModel mainViewModel,
        Views views
    ) {
        this.playerViewModel = playerViewModel;
        this.mapViewModel = mapViewModel;
        this.storeViewModel = storeViewModel;
        this.mainViewModel = mainViewModel;
        this.views = views;
    }

    public Map<GameEvent, Command> build() {
        Map<GameEvent, Command> commands = new HashMap<>();
        commands.put(GameEvent.TOOL_STORE, () -> views.getToolStore().show(storeViewModel, playerViewModel));
        commands.put(GameEvent.ANIMAL_STORE, () -> views.getAnimalStore().show(storeViewModel, playerViewModel));
        commands.put(GameEvent.FARM_STORE, () -> views.getFarmStore().show(storeViewModel, playerViewModel));
        commands.put(GameEvent.INVENTORY, () -> views.getInventory().show(playerViewModel));
        commands.put(GameEvent.SLEEP, new SleepWithPromptCommand(views.getSleep(), mapViewModel));
        commands.put(GameEvent.PLANT_PROMPT, () -> views.getPlant().showPlantPrompt(playerViewModel, mapViewModel));
        commands.put(GameEvent.COLLECT_ANIMAL, () -> views.getHarvest().showCollectAnimal(playerViewModel, mapViewModel));
        commands.put(GameEvent.EXIT, mainViewModel::shutdown);
        return commands;
    }
}
