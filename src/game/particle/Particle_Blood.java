package game.particle;

import game.world.SceneGraph;
import core.util.MathUtil;
import core.util.Shape;
import core.util.Vertex2f;

public class Particle_Blood extends Particle {

	public float size;
	public float r, g, b, a;

	public Particle_Blood(float x, float y, float z, float size, float xspeed, float yspeed, float zspeed, float r, float g, float b, float a) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = size;
		this.xprev = x;
		this.yprev = y;
		this.zprev = z;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.rotation = MathUtil.direction(0, 0, xspeed, yspeed);
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		doFriction = true;
		friction = 0.999f;
		doGravity = true;
		gravity = 0.032f * size;
		speed = 500;
		c1 = new Vertex2f(-size, -size);
		c2 = new Vertex2f(size, -size);
		c3 = new Vertex2f(-size, size);
		c4 = new Vertex2f(size, size);
		initBox();
	}

	public void update() {
		physics();
		updateBox();
		collisions();

		if (z <= 0) {
			doKill = true;
			SceneGraph.map.particlelist.add(new Particle_Splatter((int) (1500 * size), size * 2, x, y, r, g, b, a));
		}
	}

	public void render() {
		if (MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y) < 285) {
			Shape.cube(x, y, z, -size, -size, -size, size, size, size, 0, 0, rotation, r, g, b, a);
		}
	}

}
