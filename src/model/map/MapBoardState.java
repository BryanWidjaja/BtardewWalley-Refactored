package model.map;

import java.util.ArrayList;
import java.util.List;

public class MapBoardState {
    public static final char EMPTY_PLANT_TILE = '.';
    public static final char EMPTY_ANIMAL_TILE = ' ';
    public static final char PLAYER_TILE = 'P';

    private static final List<char[][]> maps = new ArrayList<>();

    private MapBoardState() {
    }

    public static void registerMaps() {
        maps.clear();
        maps.add(GameMaps.PLANT_FARM_MAP);
        maps.add(GameMaps.HOME_MAP);
        maps.add(GameMaps.ANIMAL_FARM_MAP);
    }

    public static char[][] getMap(int index) {
        return maps.get(index);
    }

    public static List<char[][]> getMaps() {
        return maps;
    }

    public static void placeAnimal(int x, int y, char symbol) {
        GameMaps.ANIMAL_FARM_MAP[x][y] = symbol;
    }

    public static void clearAnimalAt(int x, int y) {
        GameMaps.ANIMAL_FARM_MAP[x][y] = EMPTY_ANIMAL_TILE;
    }

    public static char getAnimalTile(int x, int y) {
        return GameMaps.ANIMAL_FARM_MAP[x][y];
    }

    public static boolean isAnimalTileEmpty(int x, int y) {
        return GameMaps.ANIMAL_FARM_MAP[x][y] == EMPTY_ANIMAL_TILE;
    }

    public static int animalRows() {
        return GameMaps.ANIMAL_FARM_MAP.length;
    }

    public static int animalCols() {
        return GameMaps.ANIMAL_FARM_MAP[0].length;
    }

    public static void placePlant(int x, int y, char symbol, boolean harvestable) {
        GameMaps.PLANT_FARM_MAP[x][y] = harvestable
                ? Character.toUpperCase(symbol)
                : symbol;
    }

    public static void clearPlantAt(int x, int y) {
        GameMaps.PLANT_FARM_MAP[x][y] = EMPTY_PLANT_TILE;
    }
}
