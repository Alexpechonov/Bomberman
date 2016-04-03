package com.alex.bsuir;

/**
 * Class contains information about levels
 */
public class LevelData {

	static String[] LEVEL1 = new String[]{
		"0100000000000000000",
		"0121212120202020001",
		"0000110010001111000",
	    "0020202020212121000",
	    "0000000000000000000",
	    "0021212121212120000",
	    "0000000000001000000",
	    "0021212121212000000",
	    "0000000000000000000",
	    "C40J099000",							//posx,posy,minx,maxx,miny,maxy,speedx,speedy,reverseSpeedx, reverseSpeedy
	    "E60J099000",
	    "F50J090900",
	    "F50J090901",
        "F50J090900",
        "F50J090901",
        "F50J090900",
        "F50J090901",
        "F50J090900",
        "F50J090901",
        "F50J090900",
        "F50J090901"
	};
	
	static String[] LEVEL2 = new String[]{
        "0100000000000000000",
        "1121212120202020000",
        "0000110010001111000",
        "0020202020212121000",
        "0000000000000000000",
        "0021212121212120000",
        "0000000000001000000",
        "0021212121212000000",
        "0000000000000000000",
        "C40J099000",                           //posx,posy,minx,maxx,miny,maxy,speedx,speedy,reverseSpeedx, reverseSpeedy
        "E60J099000",
        "F50J090900"
    };
	
	static String[] LEVEL3 = new String[]{
        "0100000000000000000",
        "0121212120202020001",
        "0000110010001111000",
        "0020202020212121000",
        "0000000000000000000",
        "0021212121212120000",
        "0000000000001000000",
        "0021212121212000000",
        "0000000000000000000",
        "C40J091000",                           //posx,posy,minx,maxx,miny,maxy,speedx,speedy,reverseSpeedx, reverseSpeedy
        "E60J091000"
    };

	public static String[][] levels = new String[][]{
		LEVEL1, LEVEL2, LEVEL3
	};
	
}
// A, B, C, D, E, F, G, H, I, J
// 10,11,12,13,14,15,16,17,18,19