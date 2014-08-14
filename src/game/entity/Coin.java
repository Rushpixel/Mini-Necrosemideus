package game.entity;

import game.world.SceneGraph;
import core.Assets;
import core.util.Audio;
import core.util.MathUtil;
import core.util.Vertex2f;

public abstract class Coin extends Entity {

	public float z;
	public float zspeed;
	public float gravity = 0.2f;
	public boolean falling = true;

	public float size = 1;

	public int value;

	public Coin(float x, float y, float z, float xspeed, float yspeed, float zspeed) {
		doFriction = true;
		this.x = x;
		this.y = y;
		this.z = z;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.zspeed = zspeed;
		this.speed = 10;
		c1 = new Vertex2f(-size, -size);
		c2 = new Vertex2f(size, -size);
		c3 = new Vertex2f(-size, size);
		c4 = new Vertex2f(size, size);
		initBox();
	}

	public void update() {
		AI();
		physics();
		updateBox();
		collisions();

		rotation++;
	}

	public void AI() {
		//float dist = MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y);
		float dir = MathUtil.direction(x, y, SceneGraph.player.x, SceneGraph.player.y);
		//if (dist < SceneGraph.player.pickupRange) {
			float acc = SceneGraph.player.pickupACC;
			//acc *= (1 - dist / SceneGraph.player.pickupRange);
			xspeed -= MathUtil.getXSpeed(dir, acc);
			yspeed -= MathUtil.getYSpeed(dir, acc);
		//}

		if (falling) {
			zspeed -= gravity;
			z += zspeed;
			if (z < 0) {
				z = 0;
				zspeed = -zspeed / 3;
				xspeed /= 2;
				yspeed /= 2;
				//Audio.playSoundEffect(Assets.AUDIO_COIN1, 0.1f, x, y, z);
			}
			if (Math.abs(zspeed) < 0.05f && z < 3) {
				z = 0;
				falling = false;
			}
		}
	}

	public void entityCollision(float x, float y, Entity other) {
		if (other == SceneGraph.player) {
			if (SceneGraph.player.pickup(this)) {
				doKill = true;
				Audio.playSoundEffect(Assets.AUDIO_COIN1, 1, x, y, z);
			}
		}
	}
}
