package ui.view;

import java.util.List;

import database.DatabaseRegistry;

public class MapBoard {
    public static final char EMPTY_PLANT_TILE = '.';
    public static final char EMPTY_ANIMAL_TILE = ' ';
    public static final char PLAYER_TILE = 'P';

    private MapBoard() {
    }

    public static void registerMaps() {
        List<char[][]> maps = DatabaseRegistry.getList(char[][].class);
        maps.clear();
        maps.add(GameMapView.PLANT_FARM_MAP);
        maps.add(GameMapView.HOME_MAP);
        maps.add(GameMapView.ANIMAL_FARM_MAP);
    }

    public static void placeAnimal(int x, int y, char symbol) {
        GameMapView.ANIMAL_FARM_MAP[x][y] = symbol;
    }

    public static void clearAnimalAt(int x, int y) {
        GameMapView.ANIMAL_FARM_MAP[x][y] = EMPTY_ANIMAL_TILE;
    }

    public static char getAnimalTile(int x, int y) {
        return GameMapView.ANIMAL_FARM_MAP[x][y];
    }

    public static boolean isAnimalTileEmpty(int x, int y) {
        return GameMapView.ANIMAL_FARM_MAP[x][y] == EMPTY_ANIMAL_TILE;
    }

    public static int animalRows() {
        return GameMapView.ANIMAL_FARM_MAP.length;
    }

    public static int animalCols() {
        return GameMapView.ANIMAL_FARM_MAP[0].length;
    }

    public static void placePlant(int x, int y, char symbol, boolean harvestable) {
        GameMapView.PLANT_FARM_MAP[x][y] = harvestable
                ? Character.toUpperCase(symbol)
                : symbol;
    }

    public static void clearPlantAt(int x, int y) {
        GameMapView.PLANT_FARM_MAP[x][y] = EMPTY_PLANT_TILE;
    }
}
