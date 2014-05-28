package game.world.tile;

import core.Assets;
import core.Main;
import core.util.MathUtil;
import core.util.Rectangle;
import core.util.Shape;
import game.world.Map;
import game.world.SceneGraph;

public class Wall extends Tile{
	
	public Wall(float x, float y, Map map){
		super(x, y, map);
		hasCollisions = true;
		solid = true;
		float cx = x * 32;
		float cy = y * 32;
		boundingBox = new Rectangle(cx, cy, cx + 32, cy, cx, cy + 32, cx + 32, cy + 32);
	}
	
	public void render(float x, float y) {
		//Shape.cube(x - 1, y - 1, 32, x + 33, y + 33, -8, 0.75f, 0.75f, 0.75f, 1);
		if(MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y) < 285){
			Shape.model(x + 16, y + 16, 32, 0, 0, 0, Assets.MODEL_WALL1, Assets.TEXTURE_WALL1);
		}
		if(Main.ShadowDraw)Shape.cube(x, y, 32, x + 32, y + 32, 598, 0, 0, 0, 1);
		/*renderShadow(x, y + 32, x, y, x + 32, y);
		renderShadow(x, y, x + 32, y, x + 32, y + 32);
		renderShadow(x + 32, y, x + 32, y + 32, x, y + 32);
		renderShadow(x + 32, y + 32, x, y + 32,  x, y);*/
	}
	
	public void renderShadow(float xa, float ya, float xb, float yb, float xc, float yc){
		float d = 500;
		float ra = MathUtil.direction(xa, ya, SceneGraph.player.x, SceneGraph.player.y);
		float rb = MathUtil.direction(xb, yb, SceneGraph.player.x, SceneGraph.player.y);
		float rc = MathUtil.direction(xc, yc, SceneGraph.player.x, SceneGraph.player.y);
		float xa2 = MathUtil.getXSpeed(ra, d) + xa;
		float ya2 = MathUtil.getYSpeed(ra, d) + ya;
		float xb2 = MathUtil.getXSpeed(rb, d) + xa;
		float yb2 = MathUtil.getYSpeed(rb, d) + ya;
		float xc2 = MathUtil.getXSpeed(rc, d) + xa;
		float yc2 = MathUtil.getYSpeed(rc, d) + ya;
		Shape.triangle4f(xa2, ya2, xa, ya, xa2, ya2, 0, 33);
		Shape.triangle4f(xa2, ya2, xb, yb, xb2, yb2, 0, 33);
		Shape.triangle4f(xc2, yc2, xb, yb, xb2, yb2, 0, 33);
		Shape.triangle4f(xc2, yc2, xc, yc, xb, yb, 0, 33);
	}

}
