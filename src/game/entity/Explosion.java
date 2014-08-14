package game.entity;

import core.util.MathUtil;
import core.util.Vertex2f;
import game.world.SceneGraph;

public class Explosion extends Entity {

	public float range;
	public float damage;
	public Entity[] safeList;
	public Entity parent;

	public Explosion(float x, float y, float range, float damage, Entity[] safeList, Entity parent) {
		this.x = x;
		this.y = y;
		this.range = range;
		this.damage = damage;
		this.safeList = safeList;
		this.parent = parent;
		c1 = new Vertex2f(-6f, -6f);
		c2 = new Vertex2f(6f, -6f);
		c3 = new Vertex2f(-6f, 6f);
		c4 = new Vertex2f(6f, 6f);
		initBox();
	}

	public void update() {
		for (Entity e : SceneGraph.map.entitylist) {
			doDamageTo(e);
		}
		doDamageTo(SceneGraph.player);
		doKill = true;
	}

	public boolean doesDamage(Entity e) {
		for (Entity a : safeList) {
			if (e == a) return false;
		}
		return true;
	}
	
	public void doDamageTo(Entity e){
		if (doesDamage(e)) {
			float dist = MathUtil.distance(x, y, e.x, e.y);
			float dir = MathUtil.direction(x, y, e.x, e.y);
			if (dist < range) {
				float d = damage * (1 - dist / range);
				e.Damage(d, parent, dir - 180);
				//System.out.println("Distance " + dist + " Damage " + d);
			}
		}
	}

	public void render() {
	}

}
