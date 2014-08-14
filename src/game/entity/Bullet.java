package game.entity;

import java.util.ArrayList;
import java.util.List;

import core.util.MathUtil;
import core.util.Shape;
import core.util.Vertex2f;

public class Bullet extends Entity {

	//size of bullet
	public float size;
	//how many ticks before it self destructs
	public int lifespan;
	//how much raw damage this bullet deals
	public float damage;
	//the distance the bullet will try to throw an object
	public float impact;
	//the quantity of dealt damage returned to parent as healing
	public float vampfactor;

	public Entity parent;
	public List<Entity> collisions = new ArrayList<Entity>();
	public boolean hit = false;

	public Bullet(float x, float y, float direction, float speed, int lifespan, float damage, float impact, float vampfactor, Entity parent) {
		this.x = x;
		this.y = y;
		this.xprev = x;
		this.yprev = y;
		this.rotation = direction;
		this.speed = speed;
		this.lifespan = lifespan;
		this.damage = damage;
		this.impact = impact;
		this.vampfactor = vampfactor;
		this.parent = parent;
		size = damage / 8;
		c1 = new Vertex2f(-size, -1f);
		c2 = new Vertex2f(1f, -1f);
		c3 = new Vertex2f(-1f, 1f);
		c4 = new Vertex2f(size, 1f);
		xspeed = MathUtil.getXSpeed(direction, speed);
		yspeed = MathUtil.getYSpeed(direction, speed);
		friction = 1f;
		initBox();
	}

	public void update() {
		physics();
		float dist = MathUtil.distance(x, y, xprev, yprev) + 1;
		c2.x = dist;
		c4.x = dist;
		updateBox();
		collisions();

		checkCollisionList();

		if (hit) {
			doKill = true;
			return;
		}
		lifespan--;
		if (lifespan <= 0) doKill = true;
	}

	public void render() {
		// Shape.cube(x, y, 0, c2.x, c2.y, 16, c3.x, c3.y, 18, 0, 0, rotation -
		// 180, 0.7f, 0.7f, 0f, 1);
		// Shape.cube(x, y, 17, -3, -3, -3, 3, 3, 3, 0, 0, rotation, 0.7f, 0.7f,
		// 0f, 0.0f);
		Shape.cube(x, y, 17, -2, -2, -2, 2, 2, 2, 0, 0, rotation, 1f, 0f, 0f, 1f);
	}

	public boolean doesCollideWith(Entity e) {
		if (e != parent && e.getClass() != this.getClass() && !hit) {
			return true;
		}
		return false;
	}

	public void checkCollisionList() {
		if (collisions.size() != 0) {
			float nearestE = speed * 9000;
			Entity nearest = null;
			for (Entity e : collisions) {
				Vertex2f ULUR = MathUtil.getLineLineIntersection(e.boundingBox.UL.x, e.boundingBox.UL.y, e.boundingBox.UR.x, e.boundingBox.UR.y, x, y, xprev, yprev);
				Vertex2f URDR = MathUtil.getLineLineIntersection(e.boundingBox.UR.x, e.boundingBox.UR.y, e.boundingBox.DR.x, e.boundingBox.DR.y, x, y, xprev, yprev);
				Vertex2f DRDL = MathUtil.getLineLineIntersection(e.boundingBox.DR.x, e.boundingBox.DR.y, e.boundingBox.DL.x, e.boundingBox.DL.y, x, y, xprev, yprev);
				Vertex2f DLUL = MathUtil.getLineLineIntersection(e.boundingBox.DL.x, e.boundingBox.DR.y, e.boundingBox.UL.x, e.boundingBox.UL.y, x, y, xprev, yprev);

				float result;
				try {
					float ULURd = MathUtil.distance(ULUR.x, ULUR.y, xprev, yprev);
					float URDRd = MathUtil.distance(URDR.x, URDR.y, xprev, yprev);
					float DRDLd = MathUtil.distance(DRDL.x, ULUR.y, xprev, yprev);
					float DLULd = MathUtil.distance(DLUL.x, ULUR.y, xprev, yprev);
					result = MathUtil.min(new float[] { ULURd, URDRd, DRDLd, DLULd });
				} catch (Exception ex) {
					System.out.println("Collision error caught inside " + this.getClass().getName());
					System.out.println(" Error occured with an interaction with " + e.getClass().getName());
					System.out.println();
					float corners[] = new float[4];
					corners[0] = MathUtil.distance(e.boundingBox.DL.x, e.boundingBox.DL.x, xprev, yprev);
					corners[1] = MathUtil.distance(e.boundingBox.DR.x, e.boundingBox.DR.x, xprev, yprev);
					corners[2] = MathUtil.distance(e.boundingBox.UL.x, e.boundingBox.UL.x, xprev, yprev);
					corners[3] = MathUtil.distance(e.boundingBox.UR.x, e.boundingBox.UR.x, xprev, yprev);
					result = MathUtil.min(corners);
					return;
				}

				if (result < nearestE) {
					nearestE = result;
					nearest = e;
				}

			}
			float rdamage = nearest.Damage(damage, parent, rotation);
			parent.Damage(-rdamage * vampfactor, nearest);
			nearest.xspeed += MathUtil.getXSpeed(rotation, impact);
			nearest.yspeed += MathUtil.getYSpeed(rotation, impact);
			// x -= MathUtil.getXSpeed(rotation, nearestE);
			// x -= MathUtil.getYSpeed(rotation, nearestE);
		}
	}

	public void entityCollision(float x, float y, Entity other) {
		hit = true;
		collisions.add(other);
	}

}
