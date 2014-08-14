package game.particle;

import game.world.SceneGraph;
import core.util.MathUtil;
import core.util.Shape;

public class Particle_Splatter extends Particle {

	public int lifeSpan;
	public int life = 0;
	public float size;
	public float r, g, b, a, ra;

	public Particle_Splatter(int lifeSpan, float size, float x, float y, float r, float g, float b, float a) {
		this.lifeSpan = lifeSpan;
		this.size = size;
		this.x = x;
		this.y = y;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.ra = a;
	}

	public void update() {
		if (lifeSpan != -1) {
			life++;
			if (life >= lifeSpan) doKill = true;
			size += size * (2f / lifeSpan);
			ra = a * (1 - (float) life / lifeSpan);
		}
	}

	public void render() {
		if (MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y) < 285) {
			Shape.squarer(x, y, 1, size, size, 0, 0, 0, r, g, b, ra);
		}
	}

}
