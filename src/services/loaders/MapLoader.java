package services.loaders;

import java.util.ArrayList;

import database.MapDatabase;
import views.GameMapView;

public class MapLoader {
	public static void loadMaps () {
		ArrayList<char[][]> maps = MapDatabase.getDatabase().getMaps();
		
		maps.add(GameMapView.PLANT_FARM_MAP);
		maps.add(GameMapView.HOME_MAP);
		maps.add(GameMapView.ANIMAL_FARM_MAP);
	}
}
