package services.game;

import java.util.Map;
import java.util.Scanner;

import command.Command;
import model.User;
import viewmodel.GameEvent;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class GameInteractionService {
    private static final int DEV_MODE_MONEY_GRANT = 1000000;

    private final Scanner scanner;
    private final User currentUser;
    private final PlayerViewModel playerViewModel;
    private final MapViewModel mapViewModel;
    private final Map<GameEvent, Command> eventCommands;
    private final Map<Character, Command> devModeCommands;

    public GameInteractionService(Scanner scanner, User currentUser, PlayerViewModel playerViewModel, MapViewModel mapViewModel,
                             Map<GameEvent, Command> eventCommands, Map<Character, Command> devModeCommands) {
        this.scanner = scanner;
        this.currentUser = currentUser;
        this.playerViewModel = playerViewModel;
        this.mapViewModel = mapViewModel;
        this.eventCommands = eventCommands;
        this.devModeCommands = devModeCommands;
    }

    public void update() {
        String inputStr = scanner.nextLine().toLowerCase().trim();
        if (inputStr.isEmpty()) {
            return;
        }

        if (inputStr.equals(MapViewModel.DEV_MODE_INPUT)) {
            enableDevMode();
            return;
        }

        char key = inputStr.charAt(0);
        if (tryDevModeCommand(key)) {
            return;
        }
        dispatchEvent(inputStr);
    }

    private void enableDevMode() {
        if (currentUser != null) {
            currentUser.setDevMode(true);
        }
        playerViewModel.setMoney(DEV_MODE_MONEY_GRANT);
    }

    private boolean tryDevModeCommand(char key) {
        if (currentUser == null || !currentUser.isDevMode()) {
            return false;
        }
        Command devCommand = devModeCommands.get(key);
        if (devCommand == null || !devCommand.canExecute()) {
            return false;
        }
        devCommand.execute();
        return true;
    }

    private void dispatchEvent(String inputStr) {
        GameEvent event = mapViewModel.processInput(inputStr);
        Command command = eventCommands.get(event);
        if (command != null && command.canExecute()) {
            command.execute();
        }
    }
}
