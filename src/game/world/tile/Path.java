package game.world.tile;

import java.util.Random;

import core.Assets;
import core.util.MathUtil;
import core.util.Shape;
import game.world.Map;
import game.world.SceneGraph;

public class Path extends Floor {

	public Path(float x, float y, Map map, float prob) {
		super(x, y, map, prob);
	}

	public void rollTile(Random r, Map map) {
		roll = r.nextInt(2);
		if (roll == 0) {
			tex = Assets.TEXTURE_PATH1;
		}
		if (roll == 1) {
			tex = Assets.TEXTURE_PATH2;
		}
	}

	public void render(float x, float y) {
		if (MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y) < 285) {
			Shape.model(x + 16, y + 16, -4, 180, 0, 0, Assets.MODEL_FLOOR1, tex);
			// Shape.cube(x, y, 0, -32, -32, 3, 1, 1, 1, 1);
		}
	}

}
