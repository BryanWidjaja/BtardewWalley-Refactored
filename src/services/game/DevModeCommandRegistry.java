package services.game;

import java.util.HashMap;
import java.util.Map;

import command.Command;
import command.DevModeCommand;
import command.SleepWithPromptCommand;
import command.devmode.AdvanceManyDaysCommand;
import command.devmode.DevModeTeleportCommand;
import command.devmode.GiveAllToolsCommand;
import command.devmode.GiveAnimalProductsCommand;
import command.devmode.GiveWheatSeedsCommand;
import command.devmode.InsertSampleAnimalsCommand;
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
    private final Views views;

    public DevModeCommandRegistry(User currentUser, PlayerViewModel playerViewModel, MapViewModel mapViewModel,
                                  StoreViewModel storeViewModel, Views views) {
        this.currentUser = currentUser;
        this.playerViewModel = playerViewModel;
        this.mapViewModel = mapViewModel;
        this.storeViewModel = storeViewModel;
        this.views = views;
    }

    public Map<Character, Command> build() {
        Map<Character, Command> commands = new HashMap<>();
        commands.put('r', dev(new SleepWithPromptCommand(views.getSleep(), mapViewModel)));
        commands.put('g', dev(new AdvanceManyDaysCommand(mapViewModel)));
        commands.put('u', dev(new InsertSampleAnimalsCommand(mapViewModel)));
        commands.put('t', dev(new GiveAllToolsCommand(playerViewModel)));
        commands.put('p', dev(new GiveAnimalProductsCommand(playerViewModel, storeViewModel.getGradeUtils())));
        commands.put('k', dev(new GiveWheatSeedsCommand(playerViewModel)));
        commands.put('1', dev(new DevModeTeleportCommand(mapViewModel, PLANT_FARM_INDEX)));
        commands.put('2', dev(new DevModeTeleportCommand(mapViewModel, TOWN_INDEX)));
        commands.put('3', dev(new DevModeTeleportCommand(mapViewModel, ANIMAL_FARM_INDEX)));
        return commands;
    }

    private DevModeCommand dev(Command action) {
        return new DevModeCommand(currentUser, action::execute);
    }
}
