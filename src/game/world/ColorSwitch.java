package game.world;

import java.awt.Color;

import game.world.tile.*;

public class ColorSwitch {
	
	public static final int FLOOR = new Color(74, 27, 114).getRGB();
	public static final int WALL = new Color(101, 64, 132).getRGB();
	public static final int SAFE = new Color(255, 255, 0).getRGB();
	public static final int START = new Color(252, 94, 8).getRGB();
	public static final int EXIT = new Color(0, 255, 255).getRGB();
	
	public static Tile getTile(int c, int x, int y, Map map){
		if(c == FLOOR) return new Floor(x, y, map, 1f);
		if(c == WALL) return new Wall(x, y, map);
		if(c == SAFE) return new Floor(x, y, map,0f);
		if(c == START) return new Floor(x, y, map, 0f);
		if(c == EXIT) return new Exit(x, y, map);
		return new Floor(x, y, map, 0f);
	}

}
