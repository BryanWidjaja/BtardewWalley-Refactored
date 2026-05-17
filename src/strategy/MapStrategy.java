package strategy;

import model.GameEvent;
import viewmodel.PlayerViewModel;

public interface MapStrategy {
    GameEvent checkEvent(int x, int y, PlayerViewModel playerViewModel);
}
