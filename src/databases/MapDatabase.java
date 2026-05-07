package databases;

import java.util.ArrayList;

public class MapDatabase {
	private static MapDatabase database;
	private ArrayList<char[][]> maps;
	
	private MapDatabase () {
		maps = new ArrayList<>();
	}
	
	public static MapDatabase getDatabase () {
		if (database == null)
			database = new MapDatabase();

		return database;
	}

	public ArrayList<char[][]> getMaps() {
		return maps;
	}
}
