package strategy;

import java.util.HashMap;
import java.util.Map;

import model.Coordinate;
import viewmodel.GameEvent;
import viewmodel.PlayerViewModel;

public class TownMapStrategy implements MapEventStrategy {
    static final Coordinate PORTAL_TO_PLANT_FARM = new Coordinate(10, 0);
    static final Coordinate PORTAL_TO_ANIMAL_FARM = new Coordinate(16, 43);
    static final Coordinate PLANT_FARM_ARRIVAL = new Coordinate(11, 43);
    static final Coordinate ANIMAL_FARM_ARRIVAL = new Coordinate(5, 0);

    private static final int PLANT_FARM_INDEX = 0;
    private static final int ANIMAL_FARM_INDEX = 2;

    private static final Map<Coordinate, GameEvent> EVENT_TILES = new HashMap<>();
    static {
        EVENT_TILES.put(new Coordinate(7, 21), GameEvent.SLEEP);
        EVENT_TILES.put(new Coordinate(7, 22), GameEvent.SLEEP);
        EVENT_TILES.put(new Coordinate(15, 16), GameEvent.ANIMAL_STORE);
        EVENT_TILES.put(new Coordinate(15, 17), GameEvent.ANIMAL_STORE);
        EVENT_TILES.put(new Coordinate(17, 31), GameEvent.TOOL_STORE);
        EVENT_TILES.put(new Coordinate(17, 32), GameEvent.TOOL_STORE);
    }

    @Override
    public GameEvent checkEvent(int x, int y, PlayerViewModel playerViewModel) {
        Coordinate here = new Coordinate(x, y);
        if (here.equals(PORTAL_TO_PLANT_FARM)) {
            teleport(playerViewModel, PLANT_FARM_INDEX, PLANT_FARM_ARRIVAL);
            return GameEvent.NONE;
        }
        if (here.equals(PORTAL_TO_ANIMAL_FARM)) {
            teleport(playerViewModel, ANIMAL_FARM_INDEX, ANIMAL_FARM_ARRIVAL);
            return GameEvent.NONE;
        }
        return EVENT_TILES.getOrDefault(here, GameEvent.NONE);
    }

    private void teleport(PlayerViewModel playerViewModel, int mapIndex, Coordinate destination) {
        playerViewModel.setCurrMapIndex(mapIndex);
        playerViewModel.getPosition().moveTo(destination.getX(), destination.getY());
    }
}
