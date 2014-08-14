package game.entity;

import core.util.MathUtil;
import core.util.Rectangle;
import core.util.Vertex2f;
import game.world.SceneGraph;
import game.world.tile.Tile;

public abstract class Entity {

	public float x;
	public float y;
	public float xprev;
	public float yprev;

	public float rotation;
	public float rotationprev;
	public float xspeed = 0;
	public float yspeed = 0;
	public float speed;
	public float friction = 0.95f;
	public boolean doFriction = false;

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
		x += xspeed;
		y += yspeed;
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
		for (Entity e : SceneGraph.map.entitylist) {
			if (doesCollideWith(e) && e.doesCollideWith(this) && e != this) if (boundingBox.doesCollide(e.boundingBox)) {
				Vertex2f overlap = boundingBox.moveToContact(e.boundingBox);
				entityCollision(overlap.x / 4f, overlap.y / 4f, e);
				e.entityCollision(-overlap.x / 4f, -overlap.y / 4f, this);
			}
		}
	}

	public void bounce(Rectangle box) {
		Vertex2f overlap = boundingBox.moveToContact(box);
		// xspeed = 0;
		// yspeed = 0;
		x -= overlap.x;
		y -= overlap.y;
		updateBox();
		// rotation = rotationprev;
	}

	public boolean doesCollideWith(Entity e) {
		return true;
	}

	public void entityCollision(float x, float y, Entity other) {
		this.x -= x;
		this.y -= y;
		updateBox();
	}

	public void tileCollision(Tile t) {
	}
	
	public float Damage(float damage, Entity e, float direction) {
		//returns how much damage was dealt to this entity
		//Used to handle particle from damage
		return Damage(damage, e);
	}
	
	public float Damage(float damage, Entity e) {
		//returns how much damage was dealt to this entity
		return 0;
	}
	
	public void onKill(){}

}
