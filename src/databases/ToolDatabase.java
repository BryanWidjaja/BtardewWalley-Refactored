package databases;

import java.util.ArrayList;
import models.items.Tool;

public class ToolDatabase {
	private static ToolDatabase database;
	private ArrayList<Tool> tools;
	
	private ToolDatabase () {
		tools = new ArrayList<>();
	}
	
	public static ToolDatabase getDatabase () {
		if (database == null)
			database = new ToolDatabase();

		return database;
	}

	public ArrayList<Tool> getTools() {
		return tools;
	}
}
