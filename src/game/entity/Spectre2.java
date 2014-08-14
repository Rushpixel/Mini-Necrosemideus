package game.entity;

import java.util.Random;

import game.particle.Particle_Blood;
import game.particle.Particle_Smoke;
import game.world.GameUtil;
import game.world.SceneGraph;

import org.lwjgl.util.vector.Vector2f;

import core.Assets;
import core.util.Audio;
import core.util.MathUtil;
import core.util.PathFinding;
import core.util.Shape;
import core.util.Vertex2f;

public class Spectre2 extends Entity {

	public PathFinding path;

	public boolean seen;
	public Vector2f goal;
	public Vector2f goalprev;
	public Vector2f lastUpdated;

	public float HP = 30;
	public int points = 0;
	public Entity lastHitBy;

	public int fuse = 60;
	public boolean fuseLit = false;

	public float z = 30;
	public boolean rising;
	public float alpha = 1;

	public int pause = 0;

	public float acc;
	public float damage;

	public Spectre2(float x, float y, float speed, float damage) {
		this.x = x;
		this.y = y;
		rotation = 0;
		this.speed = speed;
		this.damage = damage;
		acc = speed / 10f;
		points += 175 + (int)(50 * speed);
		c1 = new Vertex2f(-6f, -6f);
		c2 = new Vertex2f(6f, -6f);
		c3 = new Vertex2f(-6f, 6f);
		c4 = new Vertex2f(6f, 6f);
		initBox();
		goal = new Vector2f(x, y);
		goalprev = new Vector2f(x, y);
		lastUpdated = new Vector2f(x, y);
		path = new PathFinding(new Vertex2f(x, y), new Vertex2f(goal.x, goal.y), 20, 20);
	}

	public void update() {
		AI();
		physics();
		updateBox();
		collisions();
		animate();
		if (pause > 0) pause--;
		if (fuseLit) fuse--;
		if (fuse == 0) Detonate();
	}

	public void render() {
		float scale = alpha;
		Shape.model(x, y, z, scale, scale, scale, 0, 0, rotation + 270f, Assets.MODEL_SPECTRE2_BODY1, Assets.TEXTURE_SPECTRE2_BODY1);
		
		Shape.squarer(x, y, 1, 8, 8, 0, 0, rotation, 0, 0, 0, 0.4f);
	}

	public void AI() {
		// range = 245;
		if (true) {
			goal.x = SceneGraph.player.x;
			goal.y = SceneGraph.player.y;
		}
		if (MathUtil.distance(x, y, goal.x, goal.y) < 245) {
			seen = true;
		} else {
			seen = false;
		}
		if (seen) {
			if (MathUtil.distance(goal.x, goal.y, goalprev.x, goalprev.y) > 32 || MathUtil.distance(x, y, lastUpdated.x, lastUpdated.y) > 32) {
				path = new PathFinding(new Vertex2f(x, y), new Vertex2f(goal.x, goal.y), 20, 20);
				goalprev.x = goal.x;
				goalprev.y = goal.y;
				lastUpdated.x = x;
				lastUpdated.y = y;
			}

			float posx = goal.x;
			float posy = goal.y;
			if (path.Path.size() > 1) {
				float pDist = MathUtil.distance(path.Path.get(path.Path.size() - 1).parent.x * 32 + path.x, path.Path.get(path.Path.size() - 1).parent.y * 32 + path.y, path.Path.get(path.Path.size() - 1).x * 32 + path.x, path.Path.get(path.Path.size() - 1).y * 32 + path.y);
				float Dist = MathUtil.distance(x, y, path.Path.get(path.Path.size() - 1).parent.x * 32 + path.x, path.Path.get(path.Path.size() - 1).parent.y * 32 + path.y);
				if (pDist > Dist) {
					path.Path.remove(path.Path.size() - 1);
				}
			}
			if (path.Path.size() >= 2) {
				// float pos1x = path.Path.get(path.Path.size() - 1).x * 32 +
				// path.x;
				// float pos1y = path.Path.get(path.Path.size() - 1).y * 32 +
				// path.y;
				float pos2x = path.Path.get(path.Path.size() - 1).x * 32 + path.x;
				float pos2y = path.Path.get(path.Path.size() - 1).y * 32 + path.y;
				posx = (pos2x);
				posy = (pos2y);
				// posx = pos1x;
				// posy = pos1y;

			}

			if (path.Path.size() != 0) {
				float dir = MathUtil.direction(x, y, posx, posy) + 180;
				rotation = MathUtil.direction(x, y, goal.x, goal.y) + 90;
				xspeed += MathUtil.getXSpeed(dir, acc);
				yspeed += MathUtil.getYSpeed(dir, acc);
			}
		} else {
			xspeed = 0;
			yspeed = 0;
		}

		if (MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y) < 32) {
			fuseLit = true;
		}
	}

	public void animate() {
		if (rising) {
			z += acc;
			if (z > 50) rising = false;
		} else {
			z -= acc;
			if (z < 30) rising = true;
		}
		if (fuseLit) {
			float f = fuse / 20f;
			while (f > 1)
				f--;
			f = Math.abs((f) - 0.3f);
			alpha = 0.7f + f;
		}
	}

	public void Detonate() {
		SceneGraph.map.entitylist.add(new Explosion(x, y, 64, damage, new Entity[] { this }, this));
		doKill = true;
		// Smoke
		Random r = new Random();
		int nums = 5 + r.nextInt(5);
		for (int i = 0; i < nums; i++) {
			int lifeSpan = r.nextInt(10) + 5;
			float x = this.x + r.nextFloat() * 6 - 2.5f;
			float y = this.y + r.nextFloat() * 6 - 2.5f;
			float StartSize = (r.nextFloat() * 8) * (lifeSpan / 20f);
			float EndGrowth = StartSize * 8;
			float dir = r.nextFloat() * 360f;
			float dist = r.nextFloat() * 0.5f;
			float xspeed = MathUtil.getXSpeed(dir, dist);
			float yspeed = MathUtil.getYSpeed(dir, dist);
			float col = r.nextFloat() * 0.7f + 0.3f;
			SceneGraph.map.particlelist.add(new Particle_Smoke(lifeSpan, x, y, 14, xspeed, yspeed, StartSize, EndGrowth, col, col, col, 1));
		}
		// goo
		int numb = r.nextInt(20) + 10;
		for (int i = 0; i < numb; i++) {
			float size = r.nextFloat();
			float d = r.nextFloat() * 360;
			float s = r.nextFloat();
			float xspeed = MathUtil.getXSpeed(d, s);
			float yspeed = MathUtil.getYSpeed(d, s);
			float zspeed = 0;
			SceneGraph.map.particlelist.add(new Particle_Blood(x, y, 20, size * 3, xspeed, yspeed, zspeed, 0, 0, 0, 1));
		}
		// sound FX
		Audio.playSoundEffect(Assets.AUDIO_EXPLOSION1, 3, x, y, 0);
		;
	}

	public float Damage(float damage, Entity e, float direction) {
		// blood
		Random r = new Random();
		int num = r.nextInt(10) + 1;
		for (int i = 0; i < num; i++) {
			float size = r.nextFloat();
			float d = direction + r.nextFloat() * 20 - 10;
			float s = damage / 8 * r.nextFloat();
			float xspeed = MathUtil.getXSpeed(d, s);
			float yspeed = MathUtil.getYSpeed(d, s);
			float zspeed = 0;
			SceneGraph.map.particlelist.add(new Particle_Blood(x, y, z, size * 3, xspeed, yspeed, zspeed, 1, 1, 1, 1));
		}
		return Damage(damage, e);
	}

	public float Damage(float damage, Entity e) {
		if(damage > HP) damage = HP;
		if(HP < 0) damage = 0;  
		HP -= damage;
		if (HP <= 0) {
			if (!doKill) lastHitBy = e;
			doKill = true;
		}

		// sound
		Audio.playSoundEffect(Assets.AUDIO_HIT2, damage / 100, x, y, 0);

		return damage;
	}

	public void onKill() {
		Random r = new Random();
		if (lastHitBy == SceneGraph.player) {
			// coins
			GameUtil.dropCoin(points, x, y, 20, 3, r);
			// pickups
			GameUtil.dropPickup(0.1f, x, y, r);
		}
		// sound
		Audio.playSoundEffect(Assets.AUDIO_SCREAM1, 1, x, y, 0);
	}

}
