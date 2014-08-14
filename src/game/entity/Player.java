package game.entity;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import core.Assets;
import core.Game;
import core.Main;
import core.util.Audio;
import core.util.GameMouse;
import core.util.MathUtil;
import core.util.Shape;
import core.util.Vertex2f;
import game.particle.Particle_Blood;
import game.ui.Heart;
import game.ui.Slot;
import game.ui.Slot_Empty;
import game.world.SceneGraph;

public class Player extends Entity {

	public float rotSpeed = 4;
	public float acc = 0.3f;
	public float visRotate = 0;
	public float headRotate = 0;

	public float leg = 0;
	public boolean legRight = true;
	public boolean moving = false;

	public int reload = 0;
	public int heartChange = 0;

	public int score = 0;

	public float pickupRange = 96;
	public float pickupACC = 0.4f;

	public Heart heart1;
	public Heart heart2;
	public Heart heart3;
	public Heart heartc;
	
	public boolean isAlive = true;
	public int deathTimer = 60;

	public boolean flipKey = false;
	public boolean nextKey = false;
	public boolean prevKey = false;

	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		rotation = 0;
		speed = 5;
		c1 = new Vertex2f(-9.5f, -5f);
		c2 = new Vertex2f(9.5f, -5f);
		c3 = new Vertex2f(-9.5f, 5f);
		c4 = new Vertex2f(9.5f, 5f);
		initBox();

		resetHearts();
	}

	public void update() {
		if (isAlive) {
			input();
			camera();
			listener();
			physics();
			updateBox();
			collisions();
			animate();

			if (reload > 0) reload--;
			if (heartChange > 0) heartChange--;

			if (heartc.HP <= 0) {
				isAlive = false;
				Audio.playSoundEffect(Assets.AUDIO_DEATH, x, y, 0);
			}
		} else{
			postLifeUpdate();
		}

	}
	
	public void postLifeUpdate(){
		// blood
		Random r = new Random();
		int numb = r.nextInt(3) + 1;
		for (int i = 0; i < numb; i++) {
			float size = r.nextFloat();
			float d = r.nextFloat() * 360;
			float s = r.nextFloat() * 3;
			float xspeed = MathUtil.getXSpeed(d, s);
			float yspeed = MathUtil.getYSpeed(d, s);
			float zspeed = 0;
			SceneGraph.map.particlelist.add(new Particle_Blood(x, y, 20, size * 3, xspeed, yspeed, zspeed, .407f, 0f, .015f, 1));
		}
		// deathTimer
		if(deathTimer <= 0){
			SceneGraph.Respawn();
		}
		deathTimer--;
	}

	public void render() {
		float scale = 1.5f;
		float arot = rotation + (-leg / 5f) + 180;
		Shape.model(x, y, 14 * scale, scale, scale, scale, 0, leg, arot, Assets.MODEL_PLAYER_RIGHTLEG, Assets.TEXTURE_PLAYER_LEG);
		Shape.model(x, y, 14 * scale, scale, scale, scale, 0, -leg, arot, Assets.MODEL_PLAYER_LEFTLEG, Assets.TEXTURE_PLAYER_LEG);
		Shape.model(x, y, 0, scale, scale, scale, 0, 0, arot, Assets.MODEL_PLAYER_BODY, Assets.TEXTURE_PLAYER_BODY);
		Shape.model(x, y, 0, scale, scale, scale, 0, 0, headRotate - 180, Assets.MODEL_PLAYER_HEAD, Assets.TEXTURE_PLAYER_HEAD);
		// Shape.cube(x, y, 0, c1.x, c1.y, 0, c4.x, c4.y, 32, 0, 0, rotation +
		// 90, 1, 1, 1, 0.1f);
	}

	public void input() {
		doFriction = true;
		speed = 4;
		rotationprev = rotation;
		rotation += GameMouse.hMov;
		headRotate -= (headRotate - rotation) / 1.1;
		moving = false;

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			xspeed += MathUtil.getXSpeed(rotation + 180, acc / 2f);
			yspeed += MathUtil.getYSpeed(rotation + 180, acc / 2f);
			speed = 2;
			doFriction = false;
			moving = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xspeed += MathUtil.getXSpeed(rotation + 270, acc / 1.5f);
			yspeed += MathUtil.getYSpeed(rotation + 270, acc / 1.5f);
			speed = 3;
			doFriction = false;
			moving = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			xspeed += MathUtil.getXSpeed(rotation + 90, acc / 1.5f);
			yspeed += MathUtil.getYSpeed(rotation + 90, acc / 1.5f);
			speed = 3;
			doFriction = false;
			moving = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			xspeed += MathUtil.getXSpeed(rotation, acc);
			yspeed += MathUtil.getYSpeed(rotation, acc);
			speed = 4;
			doFriction = false;
			moving = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F) || Mouse.isButtonDown(1)) {
			if (!flipKey) {
				rotation += 180;
				// rotation %= 360;
				flipKey = true;
			}
		} else {
			flipKey = false;
		}
		int dWheel = Mouse.getDWheel();
		if ((Keyboard.isKeyDown(Keyboard.KEY_E) || dWheel < 0) && heartChange == 0) {
			nextHeart();
			heartChange = 10;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_Q) || dWheel > 0) && heartChange == 0) {
			prevHeart();
			heartChange = 10;
		}

		if ((Keyboard.isKeyDown(Keyboard.KEY_SPACE) || Mouse.isButtonDown(0)) && reload == 0) {
			shoot();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Game.pause = true;
			Game.pausemenu.pause = 10;
			Game.pausemenu.scroll = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			Main.AStarDraw = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
			Main.AStarDraw = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
			Main.ShadowDraw = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
			Main.ShadowDraw = true;
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

	public void camera() {
		if ((visRotate - rotation) > 2) {
			visRotate -= (visRotate - rotation) / 10;
		}
		if ((rotation - visRotate) > 2) {
			visRotate += (rotation - visRotate) / 10;
		}

		Main.camera.setX(x);
		Main.camera.setY(y);
		Main.camera.setRz(visRotate);
	}

	public void listener() {
		Audio.soundsystem.setListenerPosition(x, y, 0);
		float xd = MathUtil.getXSpeed(visRotate, 1);
		float yd = MathUtil.getYSpeed(visRotate, 1);
		Audio.soundsystem.setListenerOrientation(xd, yd, 0, 0, 0, 1);
	}

	public float Damage(float damage, Entity e, float direction) {
		// blood
		Random r = new Random();
		int num = r.nextInt(Math.abs((int) (damage / 3) + 1));
		for (int i = 0; i < num; i++) {
			float size = r.nextFloat();
			float d = direction + r.nextFloat() * 20 - 10;
			float s = damage / 8 * r.nextFloat();
			float xspeed = MathUtil.getXSpeed(d, s);
			float yspeed = MathUtil.getYSpeed(d, s);
			float zspeed = 0;
			SceneGraph.map.particlelist.add(new Particle_Blood(x, y, 20, size * 3, xspeed, yspeed, zspeed, .407f, 0f, .015f, 1));
		}
		return Damage(damage, e);
	}

	public float Damage(float damage, Entity e) {
		System.out.println("hit by" + e.getClass().getName());
		if (damage > heartc.HP) damage = heartc.HP;
		if (heartc.HP < 0) damage = 0;
		heartc.HP -= damage;
		if (heartc.HP <= 0) {
			heartc.HP = 0;
		}
		if (heartc.HP > 300) heartc.HP = 300;
		// sound
		Audio.playSoundEffect(Assets.AUDIO_HIT1, damage / 300f, x, y, 0);

		return damage;
	}
	
	public boolean doesCollideWith(Entity e) {
		return isAlive;
	}

	public void shoot() {
		float damage = 100 * ((300 - heartc.HP) / 300) + 30;
		int range = 8 * (heartc.stat_range + 1);
		float impact = 8 + 15 * (heartc.stat_impact);
		float vampfactor = 0.5f * heartc.stat_vampire;
		switch (heartc.stat_split) {
		case 0: {
			bullet(rotation, range, damage, impact, vampfactor);
		}
			break;
		case 1: {
			bullet(rotation - 10, range, damage, impact, vampfactor);
			bullet(rotation, range, damage, impact, vampfactor);
			bullet(rotation + 10, range, damage, impact, vampfactor);
		}
			break;
		case 2: {
			bullet(rotation - 15, range, damage, impact, vampfactor);
			bullet(rotation - 5, range, damage, impact, vampfactor);
			bullet(rotation + 5, range, damage, impact, vampfactor);
			bullet(rotation + 15, range, damage, impact, vampfactor);
		}
			break;
		case 3: {
			bullet(rotation - 20, range, damage, impact, vampfactor);
			bullet(rotation - 10, range, damage, impact, vampfactor);
			bullet(rotation, range, damage, impact, vampfactor);
			bullet(rotation + 10, range, damage, impact, vampfactor);
			bullet(rotation + 20, range, damage, impact, vampfactor);
		}
			break;
		}
		reload = heartc.stat_rapid > 0 ? 30 / (heartc.stat_rapid + 1) : 20;
		float reverb = (-damage / 100) * (heartc.stat_split + 1);
		xspeed += MathUtil.getXSpeed(rotation, reverb);
		yspeed += MathUtil.getYSpeed(rotation, reverb);
		rotation -= damage / 50;

		heartc.slot1.reportShot();
		heartc.slot2.reportShot();
		heartc.slot3.reportShot();
		heartc.updateSlots();

		// audio
		Audio.playSoundEffect(Assets.AUDIO_SHOT1, x, y, 0);
	}

	public void bullet(float rotation, int range, float damage, float impact, float vampfactor) {
		SceneGraph.map.entitylist.add(0, new Bullet(x, y, rotation, 12, range, damage, impact, vampfactor, this));
	}
	
	public void resetMovement(){
		xspeed = 0;
		yspeed = 0;
		rotation = 0;
		visRotate = 180;
	}
	
	public void resetHearts(){
		heart1 = new Heart();
		heart2 = new Heart();
		heart3 = new Heart();
		heartc = heart2;
		isAlive = true;
	}

	public void nextHeart() {
		if (heartc == heart1)
			heartc = heart2;
		else if (heartc == heart2)
			heartc = heart3;
		else if (heartc == heart3) heartc = heart1;
	}

	public void prevHeart() {
		if (heartc == heart1)
			heartc = heart3;
		else if (heartc == heart2)
			heartc = heart1;
		else if (heartc == heart3) heartc = heart2;
	}

	public boolean pickup(Slot slot) {
		if (heartc.slot1.getClass() == Slot_Empty.class) {
			heartc.slot1 = slot;
			heartc.updateSlots();
			return true;
		}
		if (heartc.slot2.getClass() == Slot_Empty.class) {
			heartc.slot2 = slot;
			heartc.updateSlots();
			return true;
		}
		if (heartc.slot3.getClass() == Slot_Empty.class) {
			heartc.slot3 = slot;
			heartc.updateSlots();
			return true;
		}
		return false;
	}

	public boolean pickup(Coin coin) {
		score += coin.value;
		return true;
	}

}