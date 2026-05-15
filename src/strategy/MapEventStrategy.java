package strategy;

import viewmodel.GameEvent;
import viewmodel.PlayerViewModel;

public interface MapEventStrategy {
    GameEvent checkEvent(int x, int y, PlayerViewModel playerViewModel);
}
