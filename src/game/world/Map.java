package game.world;

import java.util.ArrayList;

import game.entity.Entity;
import game.particle.Particle;
import game.world.tile.*;

public class Map extends Entity implements Cloneable {

	public int width = 32;
	public int height = 32;
	public Tile Map[][];
	
	public String Name = "";

	public ArrayList<Entity> entitylist = new ArrayList<Entity>();
	public ArrayList<Particle> particlelist = new ArrayList<Particle>();

	public Map(int width, int height, String Name) {
		this.width = width;
		this.height = height;
		this.Name = Name;
		Map = new Tile[width][height];
	}

	public void update() {
		for (int i = 0; i < entitylist.size(); i++) {
			entitylist.get(i).update();
			if (entitylist.get(i).doKill) {
				entitylist.get(i).onKill();
				entitylist.remove(i);
			}
		}
		for (int i = 0; i < particlelist.size(); i++) {
			particlelist.get(i).update();
			if (particlelist.get(i).doKill) {
				particlelist.get(i).onKill();
				particlelist.remove(i);
			}
		}
	}

	public void render() {
		for (int i = 0; i < width * height; i++) {
			Map[i % width][i / height].render(i % width * 32, i / height * 32);
		}
		for (Entity e : entitylist) {
			e.render();
		}
		for (Particle p : particlelist) {
			p.render();
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
			clone.particlelist.clear();
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
