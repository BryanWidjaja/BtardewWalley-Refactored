package views;

import models.Coordinate;

public class GameMapView {
	
	public static final int MAP_WIDTH = 44;
	public static final int MAP_HEIGHT = 21;
	
	public static final char[][] HOME_MAP = {
		"############################################".toCharArray(),
		"#                                          #".toCharArray(),
		"#                 `'::::,                  #".toCharArray(),
		"#                   ____||_                #".toCharArray(),
		"#                  /      /\\               #".toCharArray(),
		"#               __/__/\\__/  \\__            #".toCharArray(),
		"#              /__|\" '' \"| /___/\\          #".toCharArray(),
		"#              |''|\"'[]'\"|_|'_'||          #".toCharArray(),
		"#                                          #".toCharArray(),
		"#                                          #".toCharArray(),
		"                                           #".toCharArray(),
		"#            _____                         #".toCharArray(),
		"#       ____/\\    \\                        #".toCharArray(),
		"#      /\\____\\\\_/\\_\\                       #".toCharArray(),
		"#      |||''|||\"''\"|          _____        #".toCharArray(),
		"#      |||__|||_[]_|         /____/\\       #".toCharArray(),
		"#                            |\"''\"||        ".toCharArray(),
		"#                            |_[]_||       #".toCharArray(),
		"#                                          #".toCharArray(),
		"#                                          #".toCharArray(),
		"############################################".toCharArray()
	};
	
	public static final char[][] ANIMAL_FARM_MAP = {
		"############################################".toCharArray(),
		"####                        ##           ###".toCharArray(),
		"###   #                    #              ##".toCharArray(),
		"#      #                             ##    #".toCharArray(),
		"#                 #                    ##  #".toCharArray(),
		"                 ####        #             #".toCharArray(),
		"#                  #                       #".toCharArray(),
		"#                                    #     #".toCharArray(),
		"##         ##                         ##   #".toCharArray(),
		"###         ###                 #       ####".toCharArray(),
		"#####                          #          ##".toCharArray(),
		"#   ###                                    #".toCharArray(),
		"#                     ##               #   #".toCharArray(),
		"#                     #                    #".toCharArray(),
		"#    #                                     #".toCharArray(),
		"#    ##           #           #            #".toCharArray(),
		"#    ###           #         ##           ##".toCharArray(),
		"##    ##                    #             ##".toCharArray(),
		"###                                      ###".toCharArray(),
		"####                             ##    #####".toCharArray(),
		"############################################".toCharArray()
	};
	
	public static final char[][] PLANT_FARM_MAP = {
		"############################################".toCharArray(),
		"##   ###########       _____        ########".toCharArray(),
		"#      #####          /    /\\____       ####".toCharArray(),
		"#                    /_/\\_//____/\\         #".toCharArray(),
		"#                    |_[]_|||__|||        ##".toCharArray(),
		"#                                        ###".toCharArray(),
		"#       ....  ....  ....  ....  ....     ###".toCharArray(),
		"#       ....  ....  ....  ....  ....      ##".toCharArray(),
		"#       ....  ....  ....  ....  ....       #".toCharArray(),
		"##      ....  ....  ....  ....  ....       #".toCharArray(),
		"###                                        #".toCharArray(),
		"####    ++++++++++++++++++++++++++++        ".toCharArray(),
		"###                                        #".toCharArray(),
		"##      ....  ....  ....  ....  ....       #".toCharArray(),
		"#       ....  ....  ....  ....  ....       #".toCharArray(),
		"#       ....  ....  ....  ....  ....      ##".toCharArray(),
		"#       ....  ....  ....  ....  ....      ##".toCharArray(),
		"##                                       ###".toCharArray(),
		"####                      ##            ####".toCharArray(),
		"#######        ###      ######        ######".toCharArray(),
		"############################################".toCharArray()
	};
	
	public static Coordinate getAnimalFarmMapOpening() {
		return new Coordinate(5, 0);
	}
	
	public static Coordinate getPlantFarmMapOpening() {
		return new Coordinate(11, 43);
	}
}
