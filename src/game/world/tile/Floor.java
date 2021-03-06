package game.world.tile;

import java.util.Random;

import org.newdawn.slick.opengl.Texture;

import core.Assets;
import core.util.MathUtil;
import core.util.Shape;
import game.entity.Entity;
import game.world.Difficulty;
import game.world.Map;
import game.world.SceneGraph;

public class Floor extends Tile {

	public int roll = 0;
	public Texture tex;
	public float prob = 1f;

	public Entity[] spawnList;

	public Floor(float x, float y, Map map, float prob) {
		super(x, y, map);
		this.prob = prob;
	}

	public Floor(float x, float y, Map map, Entity[] spawnList) {
		super(x, y, map);
		this.spawnList = spawnList;
	}

	public void rollTile(Random r, Map map) {
		if (r.nextInt(10) == 0) {
			roll = r.nextInt(6) + 1;
		}
		if (roll == 0) {
			tex = Assets.TEXTURE_FLOOR1;
		}
		if (roll == 1) {
			tex = Assets.TEXTURE_FLOOR2;
		}
		if (roll == 2) {
			tex = Assets.TEXTURE_FLOOR3;
		}
		if (roll == 3) {
			tex = Assets.TEXTURE_FLOOR4;
		}
		if (roll == 4) {
			tex = Assets.TEXTURE_FLOOR5;
		}
		if (roll == 5) {
			tex = Assets.TEXTURE_FLOOR6;
		}
		if (roll == 6) {
			tex = Assets.TEXTURE_FLOOR7;
		}
	}

	public void rollEnemy(Random r, Map map) {
		if (spawnList == null) {
			if (r.nextFloat() < Difficulty.frequency * prob) {
				Difficulty.spawnEnemy(r, map, x, y);
			}
		} else{
			for(int i = 0; i < spawnList.length; i++){
				map.entitylist.add(spawnList[i]);
			}
		}
	}

	public void render(float x, float y) {
		if (MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y) < 285) {
			Shape.model(x + 16, y + 16, -4, 180, 0, 0, Assets.MODEL_FLOOR1, tex);
			// Shape.cube(x, y, 0, -32, -32, 3, 1, 1, 1, 1);
		}
	}

}
