package viewmodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import database.DatabaseRegistry;
import factory.animal.AnimalFactoryProvider;
import factory.plant.PlantFactoryProvider;
import iterator.Iterator;
import iterator.ListIterator;
import model.Coordinate;
import model.PlayerItem;
import model.animal.Animal;
import model.item.AnimalProduct;
import model.item.AnimalProductGrade;
import model.item.FarmProduct;
import model.item.FarmProductFreshness;
import model.item.PlantSeed;
import model.plants.Plant;
import strategy.AnimalFarmMapStrategy;
import strategy.FarmMapStrategy;
import strategy.MapEventStrategy;
import strategy.TownMapStrategy;
import ui.view.MapBoard;
import util.GradeUtils;

public class MapViewModel {
    public static final String DEV_MODE_INPUT = "devmode";
    public static final Coordinate DEV_TELEPORT_DESTINATION = new Coordinate(10, 21);
    private static final String WALL_TILES = "#+-_|/\\\"'`:,";
    private static final Set<Character> RESERVED_KEYS =
            Set.of('r', 'g', 'u', 't', 'p', 'k', '1', '2', '3');

    private static final Map<Character, int[]> MOVE_DELTAS = new HashMap<>();
    static {
        MOVE_DELTAS.put('w', new int[]{-1, 0});
        MOVE_DELTAS.put('a', new int[]{0, -1});
        MOVE_DELTAS.put('s', new int[]{1, 0});
        MOVE_DELTAS.put('d', new int[]{0, 1});
    }

    private PlayerViewModel playerViewModel;
    private Random random;
    private GradeUtils gradeUtils;
    private Animal pendingAnimal;
    private int pendingX;
    private int pendingY;
    private Map<Integer, MapEventStrategy> eventStrategies;

    public MapViewModel(PlayerViewModel playerViewModel, Random random, GradeUtils gradeUtils) {
        this.playerViewModel = playerViewModel;
        this.random = random;
        this.gradeUtils = gradeUtils;

        this.eventStrategies = new HashMap<>();
        this.eventStrategies.put(0, new FarmMapStrategy(this));
        this.eventStrategies.put(1, new TownMapStrategy());
        this.eventStrategies.put(2, new AnimalFarmMapStrategy(this));
    }

    public Animal getPendingAnimal() {
        return pendingAnimal;
    }

    public void setPendingAnimal(Animal pendingAnimal) {
        this.pendingAnimal = pendingAnimal;
    }

    public int getPendingX() {
        return pendingX;
    }

    public void setPendingX(int pendingX) {
        this.pendingX = pendingX;
    }

    public int getPendingY() {
        return pendingY;
    }

    public void setPendingY(int pendingY) {
        this.pendingY = pendingY;
    }

    public char[][] getCurrentMap() {
        return DatabaseRegistry.getList(char[][].class).get(playerViewModel.getCurrMapIndex());
    }

    public int getCurrentMapIndex() {
        return playerViewModel.getCurrMapIndex();
    }

    public GameEvent processInput(String input) {
        if (input == null || input.isEmpty()) {
            return GameEvent.NONE;
        }
        if (input.equals(DEV_MODE_INPUT)) {
            return GameEvent.NONE;
        }

        char key = input.charAt(0);
        GameEvent specialKeyEvent = dispatchSpecialKey(key);
        if (specialKeyEvent != null) {
            return specialKeyEvent;
        }

        return tryMove(key);
    }

    private GameEvent dispatchSpecialKey(char key) {
        if (key == 'e') {
            return GameEvent.INVENTORY;
        }
        if (key == 'q') {
            return GameEvent.EXIT;
        }
        if (RESERVED_KEYS.contains(key)) {
            return GameEvent.NONE;
        }
        return null;
    }

    private GameEvent tryMove(char key) {
        int[] delta = MOVE_DELTAS.get(key);
        if (delta == null) {
            return GameEvent.NONE;
        }

        Coordinate playerPos = playerViewModel.getPosition();
        int newX = playerPos.getX() + delta[0];
        int newY = playerPos.getY() + delta[1];

        char[][] currMap = getCurrentMap();
        if (!isInBounds(currMap, newX, newY) || isWall(currMap, newX, newY)) {
            return GameEvent.NONE;
        }

        commitMove(currMap, newX, newY);
        return triggerEvent(newX, newY);
    }

    private boolean isInBounds(char[][] map, int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }

    private boolean isWall(char[][] map, int x, int y) {
        return WALL_TILES.indexOf(map[x][y]) >= 0;
    }

    private void commitMove(char[][] currMap, int newX, int newY) {
        Coordinate playerPos = playerViewModel.getPosition();
        currMap[playerPos.getX()][playerPos.getY()] = playerViewModel.getCurrTile();
        playerViewModel.setCurrTile(currMap[newX][newY]);
        playerPos.moveTo(newX, newY);
        currMap[newX][newY] = MapBoard.PLAYER_TILE;
    }

    private GameEvent triggerEvent(int x, int y) {
        MapEventStrategy strategy = eventStrategies.get(playerViewModel.getCurrMapIndex());
        if (strategy != null) {
            return strategy.checkEvent(x, y, playerViewModel);
        }
        return GameEvent.NONE;
    }

    public void sleep() {
        playerViewModel.advanceDay();
        updateHarvest();
        updateGrowthTime();
        updateFreshness();
    }

    private void updateHarvest() {
        Iterator<Animal> iterator = new ListIterator<>(playerViewModel.getAnimals());
        while (iterator.hasNext()) {
            iterator.getNext().tickHarvest();
        }
    }

    private void updateGrowthTime() {
        Iterator<Plant> iterator = new ListIterator<>(playerViewModel.getPlants());
        while (iterator.hasNext()) {
            Plant plant = iterator.getNext();
            if (plant.tickGrowth()) {
                MapBoard.placePlant(
                    plant.getPosition().getX(),
                    plant.getPosition().getY(),
                    plant.getSymbol(),
                    true
                );
            }
        }
    }

    private void updateFreshness() {
        Iterator<PlayerItem> iterator = new ListIterator<>(playerViewModel.getInventory());
        while (iterator.hasNext()) {
            PlayerItem item = iterator.getNext();
            if (item.getItem() instanceof FarmProduct) {
                FarmProduct farmProduct = (FarmProduct) item.getItem();
                farmProduct.tickFreshness();
                if (farmProduct.isExpired()) {
                    iterator.remove();
                }
            }
        }
    }

    public void insertPlant(PlantSeed seed, int x, int y) {
        Plant plant = PlantFactoryProvider.getFactory(seed.getName()).createPlant(
            x,
            y,
            seed.getGrowthTime(),
            seed.getPrice(),
            false
        );
        playerViewModel.getPlants().add(plant);
    }

    public void plantSeed(PlantSeed seed, int x, int y) {
        insertPlant(seed, x, y);
        playerViewModel.setCurrTile(seed.getSymbol());
        playerViewModel.removeSeedFromInventory(seed);
    }

    public void collectPlant(int plantX, int plantY) {
        Iterator<Plant> iterator = new ListIterator<>(playerViewModel.getPlants());
        while (iterator.hasNext()) {
            Plant plant = iterator.getNext();
            if (plant.getPosition().getX() == plantX && plant.getPosition().getY() == plantY) {
                FarmProduct newProduct = new FarmProduct(
                    plant.getName(),
                    plant.getPrice(),
                    FarmProductFreshness.LEVEL_5.getLevel()
                );
                playerViewModel.addItem(newProduct, 1);
                iterator.remove();
                MapBoard.clearPlantAt(plantX, plantY);
                playerViewModel.setCurrTile(MapBoard.EMPTY_PLANT_TILE);
                break;
            }
        }
    }

    public void insertAnimal(String type, String name) {
        Animal template = findAnimalTemplate(type);
        if (template == null) {
            return;
        }

        Coordinate position = pickFreePosition();
        if (position == null) {
            return;
        }

        Animal animal = AnimalFactoryProvider.getFactory(type).createAnimal(
            name,
            template.getAnimalProduct(),
            template.getDefaultHarvestRate(),
            template.getDefaultHarvestRate(),
            position.getX(),
            position.getY(),
            template.getPrice(),
            true
        );
        playerViewModel.getAnimals().add(animal);
        MapBoard.placeAnimal(position.getX(), position.getY(), animal.getSymbol());
    }

    private Animal findAnimalTemplate(String type) {
        for (Animal animal : DatabaseRegistry.getList(Animal.class)) {
            if (animal.getType().equals(type)) {
                return animal;
            }
        }
        return null;
    }

    private Coordinate pickFreePosition() {
        int rows = MapBoard.animalRows();
        int cols = MapBoard.animalCols();
        while (true) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            if (!MapBoard.isAnimalTileEmpty(x, y)) {
                continue;
            }
            if (findAnimalAt(x, y) != null) {
                continue;
            }
            return new Coordinate(x, y);
        }
    }

    public AnimalProduct collectAnimalProduct(Animal animal) {
        AnimalProductGrade grade = gradeUtils.getGrade(playerViewModel.getDay());
        double basePrice = animal.getAnimalProduct().getBasePrice();
        AnimalProduct product = new AnimalProduct(
            animal.getAnimalProduct().getName(),
            (int) (basePrice * grade.getMultiplier()),
            grade
        );

        playerViewModel.addItem(product, 1);
        animal.collectProduct();
        return product;
    }

    public Animal findAnimalAt(int x, int y) {
        for (Animal animal : playerViewModel.getAnimals()) {
            if (animal.getPosition().getX() == x && animal.getPosition().getY() == y) {
                return animal;
            }
        }
        return null;
    }

    public void devModeClearAllPlayers() {
        for (char[][] map : DatabaseRegistry.getList(char[][].class)) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == MapBoard.PLAYER_TILE) {
                        map[i][j] = ' ';
                    }
                }
            }
        }
    }

    public void devModeTeleport(int mapIndex) {
        playerViewModel.setCurrMapIndex(mapIndex);
        playerViewModel.getPosition().moveTo(
            DEV_TELEPORT_DESTINATION.getX(),
            DEV_TELEPORT_DESTINATION.getY()
        );
        devModeClearAllPlayers();
    }
}
