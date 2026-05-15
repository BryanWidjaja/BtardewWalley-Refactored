package strategy;

import viewmodel.GameEvent;
import viewmodel.PlayerViewModel;

public class TownMapStrategy implements MapEventStrategy {
    @Override
    public GameEvent checkEvent(int x, int y, PlayerViewModel playerViewModel) {
        if (x == 10 && y == 0) {
            playerViewModel.setCurrMapIndex(0);
            playerViewModel.getPosition().moveTo(11, 43);
            return GameEvent.NONE;
        } else if (x == 16 && y == 43) {
            playerViewModel.setCurrMapIndex(2);
            playerViewModel.getPosition().moveTo(5, 0);
            return GameEvent.NONE;
        } else if (x == 7 && (y == 21 || y == 22)) {
            return GameEvent.SLEEP;
        } else if (x == 15 && (y == 16 || y == 17)) {
            return GameEvent.ANIMAL_STORE;
        } else if (x == 17 && (y == 31 || y == 32)) {
            return GameEvent.TOOL_STORE;
        }
        return GameEvent.NONE;
    }
}
