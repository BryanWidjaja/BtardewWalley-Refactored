package viewmodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

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
import model.item.PlantSeed;
import model.plants.Plant;
import strategy.AnimalFarmMapStrategy;
import strategy.FarmMapStrategy;
import strategy.MapEventStrategy;
import strategy.TownMapStrategy;
import ui.view.GameMapView;
import util.GradeUtils;

public class MapViewModel {
    private static Map<Character, BiConsumer<Coordinate, Coordinate>> moveActions = new HashMap<>();

    static {
        moveActions.put('w', (position, delta) -> delta.setX(position.getX() - 1));
        moveActions.put('a', (position, delta) -> delta.setY(position.getY() - 1));
        moveActions.put('s', (position, delta) -> delta.setX(position.getX() + 1));
        moveActions.put('d', (position, delta) -> delta.setY(position.getY() + 1));
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
        if (input == null || input.isEmpty()) return GameEvent.NONE;

        if (input.equals("devmode")) {
            return GameEvent.NONE;
        }

        char key = input.charAt(0);

        if (key == 'e') return GameEvent.INVENTORY;
        if (key == 'q') return GameEvent.EXIT;

            if ("rgutpk123".indexOf(key) >= 0) {
                return GameEvent.NONE;
            }

        char[][] currMap = getCurrentMap();
        Coordinate newPos = new Coordinate(playerViewModel.getPosition().getX(), playerViewModel.getPosition().getY());
        BiConsumer<Coordinate, Coordinate> action = moveActions.get(key);
        if (action == null) return GameEvent.NONE;
        
        action.accept(playerViewModel.getPosition(), newPos);
        int newX = newPos.getX();
        int newY = newPos.getY();

        int width = currMap.length;
        int height = currMap[0].length;

        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return GameEvent.NONE;
        }

        if (checkWall(currMap, newX, newY)) {
            return GameEvent.NONE;
        }

        currMap[playerViewModel.getPosition().getX()]
               [playerViewModel.getPosition().getY()] = playerViewModel.getCurrTile();

        playerViewModel.setCurrTile(currMap[newX][newY]);
        playerViewModel.getPosition().moveTo(newX, newY);
        currMap[newX][newY] = 'P';

        return triggerEvent(newX, newY);
    }

    private boolean checkWall(char[][] map, int x, int y) {
        String walls = "#+-_|/\\\"'`':,";
        char tile = map[x][y];

        for (int i = 0; i < walls.length(); i++) {
            if (tile == walls.charAt(i)) {
                return true;
            }
        }
        return false;
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
                GameMapView.PLANT_FARM_MAP[plant.getPosition().getX()][plant.getPosition().getY()]
                    = Character.toUpperCase(plant.getSymbol());
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
        Plant plant = PlantFactoryProvider.getFactory(seed.getName()).createPlant(x, y,
                seed.getGrowthTime(), seed.getPrice(), false);
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
                        (int) (plant.getPrice() * 1.0), // Freshness is 5, multiplier is 1.0
                        5);
                playerViewModel.addItem(newProduct, 1);
                iterator.remove();
                GameMapView.PLANT_FARM_MAP[plantX][plantY] = '.';
                playerViewModel.setCurrTile('.');
                break;
            }
        }
    }

    public void insertAnimal(String type, String name) {
        int height = GameMapView.ANIMAL_FARM_MAP.length;
        int width = GameMapView.ANIMAL_FARM_MAP[0].length;

        Animal template = null;
        for (Animal a : DatabaseRegistry.getList(Animal.class)) {
            if (a.getType().equals(type)) {
                template = a;
                break;
            }
        }
        if (template == null) return;

        while (true) {
            int animalX = random.nextInt(height);
            int animalY = random.nextInt(width);

            if (GameMapView.ANIMAL_FARM_MAP[animalX][animalY] != ' ') {
                continue;
            }

            boolean occupied = false;
            for (Animal a : playerViewModel.getAnimals()) {
                if (a.getPosition().getX() == animalX && a.getPosition().getY() == animalY) {
                    occupied = true;
                    break;
                }
            }

            if (!occupied) {
                Animal animal = AnimalFactoryProvider.getFactory(type).createAnimal(
                        name, template.getAnimalProduct(),
                        template.getDefaultHarvestRate(), template.getDefaultHarvestRate(),
                        animalX, animalY, template.getPrice(), false);
                playerViewModel.getAnimals().add(animal);
                GameMapView.ANIMAL_FARM_MAP[animalX][animalY] = animal.getSymbol();
                break;
            }
        }
    }

    public AnimalProduct collectAnimalProduct(Animal animal) {
        AnimalProductGrade grade = gradeUtils.getGrade();

        String productName = animal.getAnimalProduct();
        double basePrice = DatabaseRegistry.<String, Double>getMap(AnimalProduct.class).getOrDefault(productName, 0.0);

        AnimalProduct product = new AnimalProduct(
                productName,
                (int) (basePrice * grade.getMultiplier()),
                grade);

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
                    if (map[i][j] == 'P') {
                        map[i][j] = ' ';
                    }
                }
            }
        }
    }

    public void devModeTeleport(int mapIndex) {
        playerViewModel.setCurrMapIndex(mapIndex);
        playerViewModel.getPosition().moveTo(10, 21);
        devModeClearAllPlayers();
    }
}
