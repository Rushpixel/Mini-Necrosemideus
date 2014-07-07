package game.world;

import java.util.ArrayList;

import core.util.Shape;
import game.entity.Entity;
import game.world.tile.*;

public class Map extends Entity implements Cloneable {

	public int width = 32;
	public int height = 32;
	public Tile Map[][];

	public int northStartx;
	public int westStartx;
	public int southStartx;
	public int eastStartx;
	public int northStarty;
	public int westStarty;
	public int southStarty;
	public int eastStarty;

	public ArrayList<Entity> entitylist = new ArrayList<Entity>();

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		Map = new Tile[width][height];

		northStarty = 1;
		eastStartx = width - 2;
		southStarty = height - 2;
		westStartx = 1;
	}

	public void update() {
		for (int i = 0; i < entitylist.size(); i++) {
			entitylist.get(i).update();
			if (entitylist.get(i).doKill) entitylist.remove(i);
		}
	}

	public void render() {
		for (int i = 0; i < width * height; i++) {
			Map[i % width][i / height].render(i % width * 32, i / height * 32);
		}
		for (Entity e : entitylist) {
			e.render();
		}
	}

	public boolean testSolid(float x, float y) {
		int ix = (int) (x / 32f);
		int iy = (int) (y / 32f);

		if (ix > 0 && ix < width && iy > 0 && iy < height) {
			return Map[ix][iy].solid;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public Map clone() {
		try {
			Map clone = (Map) super.clone();
			clone.entitylist = (ArrayList<Entity>) clone.entitylist.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
