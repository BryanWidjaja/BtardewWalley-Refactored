package ui.view;

public class GameUIView {
	public static final char[][] PLAYER_INFO_UI = {
		"########{PlayerInfo}########".toCharArray(),
		"#  Day   :  #".toCharArray(),
		"#  Money :  #".toCharArray(),
		"############################".toCharArray()
	};

	public static final char[][] PLAYER_KEYBINDS_UI = {
		"#########{Keybinds}#########".toCharArray(),
		"#  W to move player UP     #".toCharArray(),
		"#  S to move player DOWN   #".toCharArray(),
		"#  A to move player LEFT   #".toCharArray(),
		"#  D to move player RIGHT  #".toCharArray(),
		"#  E to open inventory     #".toCharArray(),
		"#  Q to exit game          #".toCharArray(),
		"############################".toCharArray()
	};

	private static final int VALUE_FIELD_WIDTH = 16;

	public static char[][] buildInfoPanel(int day, double money) {
		char[][] info = new char[PLAYER_INFO_UI.length][];
		for (int i = 0; i < PLAYER_INFO_UI.length; i++) {
			info[i] = PLAYER_INFO_UI[i].clone();
		}
		info[1] = formatRow(info[1], String.valueOf(day));
		info[2] = formatRow(info[2], String.valueOf(money));
		return info;
	}

	private static char[] formatRow(char[] row, String value) {
		String line = new String(row);
		int colon = line.indexOf(":");
		String formatted = line.substring(0, colon + 2)
				+ String.format("%-" + VALUE_FIELD_WIDTH + "s", value) + "#";
		return formatted.toCharArray();
	}
}
