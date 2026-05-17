package registry;

import services.game.ViewRegistry;
import java.util.HashMap;
import java.util.Map;

import command.Command;
import command.ConfirmSleepCommand;
import model.GameEvent;
import app.GameContext;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class EventCommandRegistry {
    private final PlayerViewModel playerViewModel;
    private final MapViewModel mapViewModel;
    private final StoreViewModel storeViewModel;
    private final GameContext mainViewModel;
    private final ViewRegistry views;

    public EventCommandRegistry(
        PlayerViewModel playerViewModel,
        MapViewModel mapViewModel,
        StoreViewModel storeViewModel,
        GameContext mainViewModel,
        ViewRegistry views
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
        commands.put(GameEvent.SLEEP, new ConfirmSleepCommand(views.getSleep(), mapViewModel));
        commands.put(GameEvent.PLANT_PROMPT, () -> views.getPlant().showPlantPrompt(playerViewModel, mapViewModel));
        commands.put(GameEvent.COLLECT_ANIMAL, () -> views.getHarvest().showCollectAnimal(playerViewModel, mapViewModel));
        commands.put(GameEvent.EXIT, mainViewModel::shutdown);
        return commands;
    }
}
