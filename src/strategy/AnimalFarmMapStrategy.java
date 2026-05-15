package strategy;

import model.animal.Animal;
import viewmodel.GameEvent;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class AnimalFarmMapStrategy implements MapEventStrategy {
    private MapViewModel mapViewModel;

    public AnimalFarmMapStrategy(MapViewModel mapViewModel) {
        this.mapViewModel = mapViewModel;
    }

    @Override
    public GameEvent checkEvent(int x, int y, PlayerViewModel playerViewModel) {
        if (x == 5 && y == 0) {
            playerViewModel.setCurrMapIndex(1);
            playerViewModel.getPosition().moveTo(16, 43);
            return GameEvent.NONE;
        } else if (playerViewModel.getCurrTile() == 'C' || playerViewModel.getCurrTile() == 'c' || playerViewModel.getCurrTile() == 'S') {
            Animal animal = mapViewModel.findAnimalAt(x, y);
            mapViewModel.setPendingAnimal(animal);
            mapViewModel.setPendingX(x);
            mapViewModel.setPendingY(y);
            
            if (animal != null && animal.isHarvestable()) {
                return GameEvent.COLLECT_ANIMAL;
            }
        }
        return GameEvent.NONE;
    }
}
