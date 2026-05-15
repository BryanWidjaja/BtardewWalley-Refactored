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
	
}
