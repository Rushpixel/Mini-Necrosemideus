package game.entity;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import core.Assets;
import core.Main;
import core.util.Audio;
import core.util.MathUtil;
import core.util.PathFinding;
import core.util.Shape;
import core.util.Vertex2f;
import game.particle.Particle_Blood;
import game.particle.Particle_Smoke;
import game.ui.Slot_Impact;
import game.ui.Slot_Range;
import game.ui.Slot_Rapid;
import game.ui.Slot_Split;
import game.ui.Slot_Vampire;
import game.world.GameUtil;
import game.world.SceneGraph;

public class Spectre1 extends Entity {

	public PathFinding path;

	public boolean seen;
	public Vector2f goal;
	public Vector2f goalprev;
	public Vector2f lastUpdated;

	public float leg = 0;
	public boolean legRight = true;
	public boolean moving = false;

	public float HP = 100;
	public int points = 0;
	public Entity lastHitBy;

	public int pause = 0;

	public float acc;
	public float damage;

	public Spectre1(float x, float y, float speed, float damage) {
		this.x = x;
		this.y = y;
		rotation = 0;
		this.speed = speed;
		this.damage = damage;
		acc = speed / 30f;
		points += 125 + (int)(50 * speed);
		c1 = new Vertex2f(-9.5f, -5f);
		c2 = new Vertex2f(9.5f, -5f);
		c3 = new Vertex2f(-9.5f, 5f);
		c4 = new Vertex2f(9.5f, 5f);
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
			moving = true;
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
			moving = false;
			xspeed = 0;
			yspeed = 0;
		}
	}

	public void animate() {
		float anim = (5.625f / 4) * speed;
		if (moving) {
			if (legRight) {
				leg += anim;
				if (leg > 45) {
					legRight = !legRight;
					Audio.playSoundEffect(Assets.AUDIO_STEP1, 0.2f, x, y, 0);
				}
			} else {
				leg -= anim;
				if (leg < -45) {
					legRight = !legRight;
					Audio.playSoundEffect(Assets.AUDIO_STEP1, 0.2f, x, y, 0);
				}
			}
		} else {
			if (leg > 0) leg -= anim;
			if (leg < 0) leg += anim;
		}
	}

	public void render() {
		if (MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y) < 285) {
			float scale = 1.5f;
			float arot = rotation + (-leg / 2f) - 90;
			Shape.model(x, y, 14 * scale, scale, scale, scale, 0, leg, arot, Assets.MODEL_SPECTRE_LEGRIGHT1, Assets.TEXTURE_SPECTRE_LEGRIGHT1);
			Shape.model(x, y, 14 * scale, scale, scale, scale, 0, -leg, arot, Assets.MODEL_SPECTRE_LEGLEFT1, Assets.TEXTURE_SPECTRE_LEGLEFT1);
			Shape.model(x, y, 14 * scale, scale, scale, scale, 0, 0, arot, Assets.MODEL_SPECTRE_BODY1, Assets.TEXTURE_SPECTRE_BODY1);
			Shape.model(x, y, 27 * scale, scale, scale, scale, 0, 0, rotation + (leg / 3f) - 90, Assets.MODEL_SPECTRE_HEAD1, Assets.TEXTURE_SPECTRE_HEAD1);
			// Shape.cube(x, y, 0, c1.x, c1.y, 0, c4.x, c4.y, 32, 0, 0,
			// rotation, 1, 1, 1, 0.1f);
		}
		if (path != null && Main.AStarDraw) path.render();
	}

	public void entityCollision(float x, float y, Entity other) {
		super.entityCollision(x, y, other);
		if (other == SceneGraph.player && pause == 0) {
			float d = MathUtil.direction(other.x, other.y, x, y);
			SceneGraph.player.Damage(50 * (MathUtil.distance(0, 0, xspeed, yspeed) / speed), this, rotation);
			pause = 60;
			other.xspeed += MathUtil.getXSpeed(rotation, speed / 4);
			other.yspeed += MathUtil.getYSpeed(rotation, speed / 4);
			xspeed = -xspeed / 2;
			yspeed = -yspeed / 2;
		}
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
			SceneGraph.map.particlelist.add(new Particle_Blood(x, y, 20, size * 3, xspeed, yspeed, zspeed, 1, 1, 1, 1));
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
			//SceneGraph.player.score += points;
		}
		
		//sound
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
		// blood
		int numb = r.nextInt(20) + 10;
		for (int i = 0; i < numb; i++) {
			float size = r.nextFloat();
			float d = r.nextFloat() * 360;
			float s = r.nextFloat();
			float xspeed = MathUtil.getXSpeed(d, s);
			float yspeed = MathUtil.getYSpeed(d, s);
			float zspeed = 0;
			SceneGraph.map.particlelist.add(new Particle_Blood(x, y, 20, size * 3, xspeed, yspeed, zspeed, 1, 1, 1, 1));
		}
		
		// smoke
		int nums = 5 + r.nextInt(5);
		for (int i = 0; i < nums; i++) {
			int lifeSpan = r.nextInt(100) + 40;
			float x = this.x + r.nextFloat() * 5 - 2.5f;
			float y = this.y + r.nextFloat() * 5 - 2.5f;
			float StartSize = (r.nextFloat() * 4) * (lifeSpan / 140f);
			float EndGrowth = StartSize * 6;
			float dir = r.nextFloat() * 360f;
			float dist = r.nextFloat() * 0.5f;
			float xspeed = MathUtil.getXSpeed(dir, dist);
			float yspeed = MathUtil.getYSpeed(dir, dist);
			float col = r.nextFloat() * 0.3f + 0.7f;
			SceneGraph.map.particlelist.add(new Particle_Smoke(lifeSpan, x, y, 14, xspeed, yspeed, StartSize, EndGrowth, col, col, col, 1));
		}
		
		//sound
		Audio.playSoundEffect(Assets.AUDIO_SCREAM1, 1, x, y, 0);
	}

}
