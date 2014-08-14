package game.entity;

import game.world.SceneGraph;
import core.util.Shape;
import core.util.Vertex2f;

public class CatacombEntrance_I extends Entity{
	
	public CatacombEntrance_I(float x, float y){
		this.x = x;
		this.y = y;
		c1 = new Vertex2f(-8f, -8f);
		c2 = new Vertex2f(8f, -8f);
		c3 = new Vertex2f(-8f, 8f);
		c4 = new Vertex2f(8f, 8f);
		initBox();
	}

	public void update() {
		
	}

	public void render() {
		Shape.cube(x, y, 0, -8, -8, 0, 8, 8, 8, 0, 0, 0, 1, 1, 1, 1);
	}
	
	public boolean doesCollideWith(Entity e) {
		if (e == SceneGraph.player) {
			return true;
		}
		return false;
	}
	
	public void entityCollision(float x, float y, Entity other) {
		if (other == SceneGraph.player) {
			SceneGraph.enterCatacomb();
		}
	}

}
