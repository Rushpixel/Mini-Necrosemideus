package game.entity;

import core.util.Shape;

public class Coin_1000 extends Coin{
	
	public float size = 6;

	public Coin_1000(float x, float y, float z, float xspeed, float yspeed, float zspeed) {
		super(x, y, z, xspeed, yspeed, zspeed);
		value = 1000;
		gravity = 0.5f;
	}

	public void render() {
		Shape.cube(x, y, z, -2.5f, -2.5f, 0, 3f, 3f, 5, 0, 0, rotation, 0.57f, 0f, 0.03f, 1f);
	}

}
