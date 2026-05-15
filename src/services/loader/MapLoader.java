package services.loader;

import java.util.List;
import database.DatabaseRegistry;
import ui.view.GameMapView;

public class MapLoader {
	public static void loadMaps () {
		List<char[][]> maps = DatabaseRegistry.getList(char[][].class);
		maps.clear();
		maps.add(GameMapView.PLANT_FARM_MAP);
		maps.add(GameMapView.HOME_MAP);
		maps.add(GameMapView.ANIMAL_FARM_MAP);
	}
}
