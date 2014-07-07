package game.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import core.Assets;
import core.Game;
import core.Main;
import core.util.GameMouse;
import core.util.MathUtil;
import core.util.Shape;
import core.util.Vertex2f;
import game.ui.Heart;
import game.ui.Slot_Shotgun;
import game.world.Difficulty;
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

	public Heart heart1;
	public Heart heart2;
	public Heart heart3;
	public Heart heartc;

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

		heart1 = new Heart();
		heart2 = new Heart();
		heart3 = new Heart();
		heart3.slot1 = new Slot_Shotgun();
		heartc = heart2;
	}

	public void update() {
		input();
		camera();
		physics();
		updateBox();
		collisions();
		animate();

		if (reload > 0) reload--;
		if (heartChange > 0) heartChange--;
		heartc.updateSlots(false);

	}

	public void render() {
		float scale = 1.5f;
		float arot = rotation + (-leg / 5f) + 180;
		Shape.model(x, y, 14 * scale, scale, scale, scale, 0, leg, arot, Assets.MODEL_PLAYER_RIGHTLEG, Assets.TEXTURE_PLAYER_LEG);
		Shape.model(x, y, 14 * scale, scale, scale, scale, 0, -leg, arot, Assets.MODEL_PLAYER_LEFTLEG, Assets.TEXTURE_PLAYER_LEG);
		Shape.model(x, y, 0, scale, scale, scale, 0, 0, arot, Assets.MODEL_PLAYER_BODY, Assets.TEXTURE_PLAYER_BODY);
		Shape.model(x, y, 0, scale, scale, scale, 0, 0, headRotate - 180, Assets.MODEL_PLAYER_HEAD, Assets.TEXTURE_PLAYER_HEAD);
		//Shape.cube(x, y, 0, c1.x, c1.y, 0, c4.x, c4.y, 32, 0, 0, rotation + 90, 1, 1, 1, 0.1f);
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

		if (Keyboard.isKeyDown(Keyboard.KEY_E) && heartChange == 0) {
			nextHeart();
			heartChange = 10;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q) && heartChange == 0) {
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
	
	public void animate(){
		float anim = (5.625f / 4) * speed;
		if (moving) {
			if (legRight) {
				leg += anim;
				if (leg > 45) legRight = !legRight;
			} else {
				leg -= anim;
				if (leg < -45) legRight = !legRight;
			}
		} else{
			if(leg > 0) leg -= anim;
			if(leg < 0) leg += anim;
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

	public void Damage(float damage) {
		heartc.HP -= damage;
		if (heartc.HP <= 0) {
			heartc.HP = 0;
			// nextHeart();
			SceneGraph.newGame();
		}
	}

	public void shoot() {
		float damage = 100 * ((300 - heartc.HP) / 300) + 30;
		SceneGraph.map.entitylist.add(0, new Bullet(x, y, rotation, 32, 9, damage, this));
		heartc.updateSlots(true);
		reload = 20;
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
	
}
