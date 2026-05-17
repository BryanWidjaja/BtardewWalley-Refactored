package services.game;

import ui.view.HudView;
import model.map.MapBoardState;
import util.ConsoleUtils;
import ui.TileColors;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class GameRenderingService {
    private final MapViewModel mapViewModel;
    private final PlayerViewModel playerViewModel;
    private final ConsoleUtils consoleUtils;

    public GameRenderingService(
        MapViewModel mapViewModel,
        PlayerViewModel playerViewModel,
        ConsoleUtils consoleUtils
    ) {
        this.mapViewModel = mapViewModel;
        this.playerViewModel = playerViewModel;
        this.consoleUtils = consoleUtils;
    }

    public void render() {
        consoleUtils.spaceConsole();
        char[][] map = preparedMap();
        char[][] infoPanel = HudView.buildInfoPanel(playerViewModel.getDay(), playerViewModel.getMoney());
        char[][] keybindPanel = HudView.PLAYER_KEYBINDS_UI;

        renderRows(map, infoPanel, keybindPanel);
    }

    private char[][] preparedMap() {
        char[][] map = mapViewModel.getCurrentMap();
        map[playerViewModel.getPosition().getX()][playerViewModel.getPosition().getY()] = MapBoardState.PLAYER_TILE;
        return map;
    }

    private void renderRows(char[][] map, char[][] infoPanel, char[][] keybindPanel) {
        int mapHeight = map.length;
        int mapWidth = map[0].length;
        int uiHeight = infoPanel.length + 1 + keybindPanel.length;
        int totalRows = Math.max(mapHeight, uiHeight);

        for (int row = 0; row < totalRows; row++) {
            StringBuilder line = new StringBuilder();
            appendMapRow(line, map, row, mapWidth);
            line.append("  ");
            appendUIRow(line, infoPanel, keybindPanel, row);
            System.out.println(line);
        }
    }

    private void appendMapRow(StringBuilder line, char[][] map, int row, int mapWidth) {
        if (row >= map.length) {
            line.append(" ".repeat(mapWidth));
            return;
        }
        for (int col = 0; col < map[row].length; col++) {
            line.append(TileColors.colorize(map[row][col]));
        }
    }

    private void appendUIRow(StringBuilder line, char[][] infoPanel, char[][] keybindPanel, int row) {
        if (row < infoPanel.length) {
            line.append(new String(infoPanel[row]));
        } else if (row == infoPanel.length) {
            line.append(" ".repeat(infoPanel[0].length));
        } else if (row < infoPanel.length + 1 + keybindPanel.length) {
            line.append(new String(keybindPanel[row - infoPanel.length - 1]));
        } else {
            line.append(" ".repeat(keybindPanel[0].length));
        }
    }
}
