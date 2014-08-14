package game.world;

import java.awt.Color;

import game.entity.CatacombEntrance_I;
import game.entity.Entity;
import game.world.tile.*;
import game.world.tile.Void;

public class ColorSwitch {
	
	public static final int FLOOR = new Color(74, 27, 114).getRGB();
	public static final int WALL = new Color(101, 64, 132).getRGB();
	public static final int SAFE = new Color(255, 255, 0).getRGB();
	public static final int START = new Color(252, 94, 8).getRGB();
	public static final int EXIT = new Color(0, 255, 255).getRGB();
	public static final int GRASS1 = new Color(99, 143, 16).getRGB();
	public static final int PATH = new Color(103, 126, 63).getRGB();
	public static final int RESPAWN = new Color(156, 0, 14).getRGB();
	public static final int PILLAR = new Color(154, 154, 154).getRGB();
	public static final int CATACOMBENTRANCE = new Color(38, 39, 122).getRGB();
	public static final int VOID = new Color(0, 0, 0).getRGB();
	
	public static Tile getTile(int c, int x, int y, Map map){
		if(c == FLOOR) return new Floor(x, y, map, 1f);
		if(c == WALL) return new Wall(x, y, map);
		if(c == SAFE) return new Floor(x, y, map,0f);
		if(c == START) return new Floor(x, y, map, 0f);
		if(c == EXIT) return new Exit(x, y, map);
		if(c == GRASS1) return new Grass(x, y, map, 0);
		if(c == PATH) return new Path(x, y, map, 0);
		if(c == CATACOMBENTRANCE) return new Grass(x, y, map, new Entity[] {new CatacombEntrance_I(x * 32 + 16, y * 32 + 16)});
		if(c == RESPAWN) return new Grass(x, y, map, 0);
		if(c == VOID) return new Void(x, y, map);
		if(c == PILLAR) return new Void(x, y, map);
		return new Floor(x, y, map, 0f);
	}

}
