package strategy;

import java.util.Set;

import model.Coordinate;
import model.animal.Animal;
import viewmodel.GameEvent;
import viewmodel.MapViewModel;
import viewmodel.PlayerViewModel;

public class AnimalFarmMapStrategy implements MapEventStrategy {
    private static final Coordinate PORTAL_TO_TOWN = new Coordinate(5, 0);
    private static final int TOWN_MAP_INDEX = 1;
    private static final Set<Character> ANIMAL_TILES = Set.of('C', 'c', 'S');

    private final MapViewModel mapViewModel;

    public AnimalFarmMapStrategy(MapViewModel mapViewModel) {
        this.mapViewModel = mapViewModel;
    }

    @Override
    public GameEvent checkEvent(int x, int y, PlayerViewModel playerViewModel) {
        if (x == PORTAL_TO_TOWN.getX() && y == PORTAL_TO_TOWN.getY()) {
            playerViewModel.setCurrMapIndex(TOWN_MAP_INDEX);
            playerViewModel.getPosition().moveTo(
                    TownMapStrategy.PORTAL_TO_ANIMAL_FARM.getX(),
                    TownMapStrategy.PORTAL_TO_ANIMAL_FARM.getY());
            return GameEvent.NONE;
        }

        if (!ANIMAL_TILES.contains(playerViewModel.getCurrTile())) {
            return GameEvent.NONE;
        }

        Animal animal = mapViewModel.findAnimalAt(x, y);
        mapViewModel.setPendingAnimal(animal);
        mapViewModel.setPendingX(x);
        mapViewModel.setPendingY(y);

        if (animal != null && animal.isHarvestable()) {
            return GameEvent.COLLECT_ANIMAL;
        }
        return GameEvent.NONE;
    }
}
