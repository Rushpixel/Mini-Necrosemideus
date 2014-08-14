package game.world.tile;

import java.util.Random;

import core.Assets;
import core.util.MathUtil;
import core.util.Shape;
import game.entity.Entity;
import game.world.Map;
import game.world.SceneGraph;

public class Grass extends Floor {

	public Grass(float x, float y, Map map, float prob) {
		super(x, y, map, prob);
	}
	
	public Grass(float x, float y, Map map, Entity[] spawnList) {
		super(x, y, map, spawnList);
	}

	public void rollTile(Random r, Map map) {
		if (r.nextInt(10) == 0) {
			//roll = r.nextInt(10) + 1;
		}
		if (roll == 0) {
			tex = Assets.TEXTURE_GRASS1;
		}
	}

	public void render(float x, float y) {
		if (MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y) < 285) {
			Shape.model(x + 16, y + 16, -4, 180, 0, 0, Assets.MODEL_FLOOR1, tex);
			// Shape.cube(x, y, 0, -32, -32, 3, 1, 1, 1, 1);
		}
	}

}
