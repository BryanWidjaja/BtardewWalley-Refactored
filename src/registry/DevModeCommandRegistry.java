package registry;

import services.game.ViewRegistry;
import java.util.HashMap;
import java.util.Map;

import command.Command;
import command.devmode.DevModeGuardCommand;
import command.ConfirmSleepCommand;
import command.devmode.AdvanceManyDaysCommand;
import command.devmode.TeleportCommand;
import command.devmode.GiveAllToolsCommand;
import command.devmode.GiveAnimalProductsCommand;
import command.devmode.GiveAllSeedsCommand;
import command.devmode.GiveSampleAnimalsCommand;
import model.User;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;
import viewmodel.StoreViewModel;

public class DevModeCommandRegistry {
    private static final int PLANT_FARM_INDEX = 0;
    private static final int TOWN_INDEX = 1;
    private static final int ANIMAL_FARM_INDEX = 2;

    private final User currentUser;
    private final PlayerViewModel playerViewModel;
    private final MapViewModel mapViewModel;
    private final StoreViewModel storeViewModel;
    private final ViewRegistry views;

    public DevModeCommandRegistry(
        User currentUser,
        PlayerViewModel playerViewModel,
        MapViewModel mapViewModel,
        StoreViewModel storeViewModel,
        ViewRegistry views
    ) {
        this.currentUser = currentUser;
        this.playerViewModel = playerViewModel;
        this.mapViewModel = mapViewModel;
        this.storeViewModel = storeViewModel;
        this.views = views;
    }

    public Map<Character, Command> build() {
        Map<Character, Command> commands = new HashMap<>();
        commands.put('r', dev(new ConfirmSleepCommand(views.getSleep(), mapViewModel)));
        commands.put('g', dev(new AdvanceManyDaysCommand(mapViewModel)));
        commands.put('u', dev(new GiveSampleAnimalsCommand(mapViewModel)));
        commands.put('t', dev(new GiveAllToolsCommand(playerViewModel)));
        commands.put('p', dev(new GiveAnimalProductsCommand(playerViewModel, storeViewModel.getGradeUtils())));
        commands.put('k', dev(new GiveAllSeedsCommand(playerViewModel)));
        commands.put('1', dev(new TeleportCommand(mapViewModel, PLANT_FARM_INDEX)));
        commands.put('2', dev(new TeleportCommand(mapViewModel, TOWN_INDEX)));
        commands.put('3', dev(new TeleportCommand(mapViewModel, ANIMAL_FARM_INDEX)));
        return commands;
    }

    private DevModeGuardCommand dev(Command action) {
        return new DevModeGuardCommand(currentUser, action::execute);
    }
}
