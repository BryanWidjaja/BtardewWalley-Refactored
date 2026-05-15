package services.game;

import java.util.Map;
import java.util.Scanner;

import command.Command;
import model.User;
import viewmodel.GameEvent;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class GameInteractionService {
    private Scanner scanner;
    private User currentUser;
    private PlayerViewModel playerViewModel;
    private MapViewModel mapViewModel;
    private Map<GameEvent, Command> eventCommands;
    private Map<Character, Command> devModeCommands;

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
        if (inputStr.isEmpty()) return;

        if (inputStr.equals("devmode")) {
            if (currentUser != null) currentUser.setDevMode(true);
            playerViewModel.setMoney(1000000);
            return;
        }

        char key = inputStr.charAt(0);

        if (currentUser != null && currentUser.isDevMode()) {
            Command devModeCommand = devModeCommands.get(key);
            if (devModeCommand != null && devModeCommand.canExecute()) {
                devModeCommand.execute();
                return;
            }
        }

        GameEvent event = mapViewModel.processInput(inputStr);
        Command command = eventCommands.get(event);
        if (command != null && command.canExecute()) {
            command.execute();
        }
    }
}
