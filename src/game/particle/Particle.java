package game.particle;

import game.entity.Entity;
import game.world.SceneGraph;
import game.world.tile.Tile;
import core.util.MathUtil;
import core.util.Rectangle;
import core.util.Vertex2f;

public abstract class Particle{
	
	public float x;
	public float y;
	public float z;
	public float xprev;
	public float yprev;
	public float zprev;

	public float rotation;
	public float xspeed = 0;
	public float yspeed = 0;
	public float zspeed = 0;
	public float speed;
	public float friction = 0.99f;
	public float gravity = 0.08f;
	public boolean doFriction = false;
	public boolean doGravity = false;

	public Vertex2f c1;
	public Vertex2f c2;
	public Vertex2f c3;
	public Vertex2f c4;
	public Rectangle boundingBox;

	public boolean doKill = false;
	
	public abstract void update();
	
	public abstract void render();
	
	public void physics() {
		if (doFriction) {
			xspeed *= friction;
			yspeed *= friction;
		}
		if(doGravity) {
			zspeed -= gravity;
		}
		float cspeed = MathUtil.distance(0, 0, xspeed, yspeed);
		if (cspeed > speed) {
			float rot = MathUtil.direction(xspeed, yspeed, 0, 0);
			xspeed = MathUtil.getXSpeed(rot % 360, speed);
			yspeed = MathUtil.getYSpeed(rot % 360, speed);
		}
		if (cspeed < 0.1) {
			xspeed = 0;
			yspeed = 0;
		}
		xprev = x;
		yprev = y;
		zprev = z;
		x += xspeed;
		y += yspeed;
		z += zspeed;
	}
	
	public void initBox() {
		boundingBox = new Rectangle(0, 0, 0, 0, 0, 0, 0, 0);
		updateBox();
	}
	
	public void updateBox() {
		boundingBox.UL = MathUtil.rotateMatrix2D(c1, rotation - 180);
		boundingBox.UR = MathUtil.rotateMatrix2D(c2, rotation - 180);
		boundingBox.DL = MathUtil.rotateMatrix2D(c3, rotation - 180);
		boundingBox.DR = MathUtil.rotateMatrix2D(c4, rotation - 180);
		boundingBox.UL = MathUtil.shiftMatrix2D(boundingBox.UL, x, y);
		boundingBox.UR = MathUtil.shiftMatrix2D(boundingBox.UR, x, y);
		boundingBox.DL = MathUtil.shiftMatrix2D(boundingBox.DL, x, y);
		boundingBox.DR = MathUtil.shiftMatrix2D(boundingBox.DR, x, y);
		boundingBox.calcAxis();
	}
	
	public void collisions() {
		for (int i = 0; i < SceneGraph.map.width * SceneGraph.map.height; i++) {
			Tile t = SceneGraph.map.Map[i % SceneGraph.map.width][i / SceneGraph.map.height];
			if (t.doesCollide(this)) {
				if (boundingBox.doesCollide(t.boundingBox)) {
					tileCollision(t);
					t.reportCollision(this);
					if(t.isSolidFor(this)) bounce(t.boundingBox);
				}
			}
		}
	}
	
	public void bounce(Rectangle box) {
		Vertex2f overlap = boundingBox.moveToContact(box);
		x -= overlap.x;
		y -= overlap.y;
		updateBox();
	}
	
	public void tileCollision(Tile t) {
	}
	
	public void onKill(){}

}
