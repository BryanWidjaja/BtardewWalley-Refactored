package strategy;

import java.util.HashMap;
import java.util.Map;

import model.Coordinate;
import ui.view.MapBoard;
import viewmodel.GameEvent;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class FarmMapStrategy implements MapEventStrategy {
    private static final Coordinate PORTAL_TO_TOWN = new Coordinate(11, 43);
    private static final int TOWN_MAP_INDEX = 1;

    private static final Map<Coordinate, GameEvent> EVENT_TILES = new HashMap<>();
    static {
        EVENT_TILES.put(new Coordinate(4, 23), GameEvent.FARM_STORE);
        EVENT_TILES.put(new Coordinate(4, 24), GameEvent.FARM_STORE);
    }

    private final MapViewModel mapViewModel;

    public FarmMapStrategy(MapViewModel mapViewModel) {
        this.mapViewModel = mapViewModel;
    }

    @Override
    public GameEvent checkEvent(int x, int y, PlayerViewModel playerViewModel) {
        Coordinate here = new Coordinate(x, y);
        if (here.equals(PORTAL_TO_TOWN)) {
            playerViewModel.setCurrMapIndex(TOWN_MAP_INDEX);
            playerViewModel.getPosition().moveTo(
                TownMapStrategy.PORTAL_TO_PLANT_FARM.getX(),
                TownMapStrategy.PORTAL_TO_PLANT_FARM.getY()
            );
            return GameEvent.NONE;
        }

        GameEvent storeEvent = EVENT_TILES.get(here);
        if (storeEvent != null) {
            return storeEvent;
        }

        char tile = playerViewModel.getCurrTile();
        if (tile == MapBoard.EMPTY_PLANT_TILE) {
            mapViewModel.setPendingX(x);
            mapViewModel.setPendingY(y);
            return GameEvent.PLANT_PROMPT;
        }
        if (Character.isUpperCase(tile) && tile != MapBoard.PLAYER_TILE) {
            mapViewModel.collectPlant(x, y);
            return GameEvent.NONE;
        }
        return GameEvent.NONE;
    }
}
