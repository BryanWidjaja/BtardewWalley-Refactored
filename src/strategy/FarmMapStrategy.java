package strategy;

import viewmodel.GameEvent;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class FarmMapStrategy implements MapEventStrategy {
    private MapViewModel mapViewModel;

    public FarmMapStrategy(MapViewModel mapViewModel) {
        this.mapViewModel = mapViewModel;
    }

    @Override
    public GameEvent checkEvent(int x, int y, PlayerViewModel playerViewModel) {
        if (x == 11 && y == 43) {
            playerViewModel.setCurrMapIndex(1);
            playerViewModel.getPosition().moveTo(10, 0);
            return GameEvent.NONE;
        } else if (x == 4 && (y == 23 || y == 24)) {
            return GameEvent.FARM_STORE;
        } else if (playerViewModel.getCurrTile() == '.') {
            mapViewModel.setPendingX(x);
            mapViewModel.setPendingY(y);
            return GameEvent.PLANT_PROMPT;
        } else if (Character.isUpperCase(playerViewModel.getCurrTile()) && playerViewModel.getCurrTile() != 'P') {
            mapViewModel.collectPlant(x, y);
            return GameEvent.NONE;
        }
        return GameEvent.NONE;
    }
}
