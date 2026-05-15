package services.game;

import util.StringUtils;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;
import ui.view.GameUIView;
import util.ConsoleUtils;

public class GameRenderingService {
    private final MapViewModel mapViewModel;
    private final PlayerViewModel playerViewModel;
    private final ConsoleUtils consoleUtils;

    public GameRenderingService(MapViewModel mapViewModel, PlayerViewModel playerViewModel, ConsoleUtils consoleUtils) {
        this.mapViewModel = mapViewModel;
        this.playerViewModel = playerViewModel;
        this.consoleUtils = consoleUtils;
    }

    public void render() {
        consoleUtils.spaceConsole();
        char[][] currMap = mapViewModel.getCurrentMap();

        currMap[playerViewModel.getPosition().getX()]
               [playerViewModel.getPosition().getY()] = 'P';

        char[][] infoUI = new char[GameUIView.PLAYER_INFO_UI.length][];
        for (int i = 0; i < GameUIView.PLAYER_INFO_UI.length; i++) {
            infoUI[i] = GameUIView.PLAYER_INFO_UI[i].clone();
        }

        char[][] keybindUI = GameUIView.PLAYER_KEYBINDS_UI;

        for (int i = 1; i <= 2; i++) {
            String line = new String(infoUI[i]);
            int colon = line.indexOf(":");
            String value = i == 1
                    ? String.valueOf(playerViewModel.getDay())
                    : String.valueOf(playerViewModel.getMoney());
            int width = 16;
            line = line.substring(0, colon + 2)
                    + String.format("%-" + width + "s", value) + "#";
            infoUI[i] = line.toCharArray();
        }

        int mapHeight = currMap.length;
        int mapWidth = currMap[0].length;
        int uiHeight = infoUI.length + 1 + keybindUI.length;
        int totalRows = Math.max(mapHeight, uiHeight);

        for (int row = 0; row < totalRows; row++) {
            StringBuilder line = new StringBuilder();

            if (row < mapHeight) {
                for (int col = 0; col < currMap[row].length; col++) {
                    line.append(StringUtils.colorize(currMap[row][col]));
                }
            } else {
                line.append(" ".repeat(mapWidth));
            }

            line.append("  ");

            if (row < infoUI.length) {
                line.append(new String(infoUI[row]));
            } else if (row == infoUI.length) {
                line.append(" ".repeat(infoUI[0].length));
            } else if (row < infoUI.length + 1 + keybindUI.length) {
                line.append(new String(keybindUI[row - infoUI.length - 1]));
            } else {
                line.append(" ".repeat(keybindUI[0].length));
            }

            System.out.println(line);
        }
    }
}
